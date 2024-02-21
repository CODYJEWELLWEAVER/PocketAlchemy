package com.android.pocketalchemy.editrecipe.selectingredient

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.android.pocketalchemy.R
import com.android.pocketalchemy.model.Ingredient
import com.android.pocketalchemy.ui.common.LoadingIndicator
import com.android.pocketalchemy.ui.common.PaTopAppBar
import kotlinx.coroutines.Dispatchers

private const val TAG = "SelectIngredient"

/**
 * Displays list of paged ingredient data for
 * selection
 * @param selectIngredientViewModel
 * @param onClickBackButton lambda for navigating back to category list
 */
@Composable
fun SelectIngredient(
    selectIngredientViewModel: SelectIngredientViewModel = hiltViewModel(),
    onClickBackButton: () -> Unit,
) {
    Popup {
        Scaffold(
            topBar = {
                PaTopAppBar(
                    titleId = R.string.select_ingredient_title,
                )
            },
            bottomBar = {
                SelectIngredientNavBar(onClickBackButton)
            }
        ) { scaffoldPadding ->
            val ingredientsList by selectIngredientViewModel.ingredientPagingState.collectAsState()
            val ioDispatcher = Dispatchers.IO
            val ingredients = ingredientsList.collectAsLazyPagingItems(ioDispatcher)

            var selectedIngredient: Ingredient? by remember { mutableStateOf(null) }

            Surface(
                modifier = Modifier
                    .fillMaxSize(1f)
                    .padding(scaffoldPadding)
            ) {
                LazyColumn(
                    contentPadding = PaddingValues(8.dp)
                ) {
                    items(ingredients.itemCount) { index ->
                        val ingredient: Ingredient? = ingredients[index]
                        ingredient?.let {
                            IngredientCard(ingredient = it) { ingredient ->
                                Log.d(TAG, "Selected ingredient: ${ingredient.description}")
                                selectedIngredient = ingredient
                            }
                        }
                    }

                    // Initial loading UI
                    when (ingredients.loadState.refresh) {
                        is LoadState.Error -> {
                            // TODO: Show loading error
                        }
                        is LoadState.Loading -> item {
                            Box(
                                modifier = Modifier.fillParentMaxSize(1f)
                            ) {
                                LoadingIndicator()
                            }
                        }
                        else -> {}
                    }

                    // Appending paging data UI
                    when (ingredients.loadState.append) {
                        is LoadState.Loading -> {
                            item {
                                Column(
                                    modifier = Modifier
                                        .fillMaxWidth(1f)
                                        .padding(8.dp),
                                ) {
                                    LoadingIndicator()
                                }
                            }
                        }
                        is LoadState.Error -> {
                            // TODO: Show error UI
                        }
                        else -> {}
                    }
                }
            }

            if (selectedIngredient != null) {
                SelectIngredientQuantityDialog(
                    ingredient = selectedIngredient!!,
                    onDismiss = { selectedIngredient = null },
                    onAddIngredient = { _, _ -> {} }
                )
            }
        }
    }
}

/**
 * Card used for displaying basic ingredient information in
 * ingredient selection pop up.
 * @param ingredient
 * @param onClickAddIngredient callback for adding selected ingredient
 * to current recipe
 */
@Composable
fun IngredientCard(
    ingredient: Ingredient,
    onClickAddIngredient: (Ingredient) -> Unit,
) {
    Card(
        modifier = Modifier
            .fillMaxWidth(1f)
            .padding(8.dp),
        shape = CardDefaults.elevatedShape,
        elevation = CardDefaults.cardElevation(4.dp),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth(1f)
                .padding(16.dp)
                .heightIn(min = 100.dp),
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            IngredientDescription(ingredientDesc = ingredient.fancyDescription)

            AddIngredientButton {
                onClickAddIngredient(ingredient)
            }
        }
    }
}

@Composable
fun IngredientDescription(ingredientDesc: String) {
    Text(
        text = ingredientDesc,
        style = MaterialTheme.typography.headlineSmall,
        textAlign = TextAlign.Center,
        modifier = Modifier.fillMaxWidth(1f),
    )
}

@Composable
fun AddIngredientButton(
    onClickAddIngredient: () -> Unit
) {
    Button(onClick = onClickAddIngredient) {
        Text(text = stringResource(id = R.string.add_ingredient_label))
    }
}