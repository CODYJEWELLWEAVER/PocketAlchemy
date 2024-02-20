package com.android.pocketalchemy.editrecipe.selectingredient

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.android.pocketalchemy.R
import com.android.pocketalchemy.model.Ingredient
import com.android.pocketalchemy.model.IngredientCategory
import com.android.pocketalchemy.model.displayLabelRes
import com.android.pocketalchemy.ui.common.LoadingIndicator
import com.android.pocketalchemy.ui.common.PaTopAppBar
import kotlinx.coroutines.Dispatchers

/**
 * Popup to allow users to select ingredient to add to
 * recipe.
 * @param selectIngredientViewModel
 * @param onClickBackButton lambda for closing pop-up screen
 */
@Composable
fun SelectIngredientCategory(
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
            Surface(
                modifier = Modifier
                    .fillMaxSize(1f)
                    .padding(scaffoldPadding)
            ) {
                val selectedCategory by selectIngredientViewModel.selectedCategory.collectAsState()
                val isCategorySelected = selectedCategory != null

                LazyColumn(
                    modifier = Modifier.padding(8.dp),
                ) {
                    items(IngredientCategory.entries) {
                        IngredientCategoryCard(category = it) { categoryName ->
                            selectIngredientViewModel.updateCategory(categoryName)
                        }
                    }
                }

                if (isCategorySelected) {
                    SelectIngredient(selectIngredientViewModel) {
                        selectIngredientViewModel.updateCategory(null)
                    }
                }

                BackHandler(
                    enabled = isCategorySelected
                ) {
                    selectIngredientViewModel.updateCategory(null)
                }
            }
        }
    }
}

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
    val defaultDispatcher = Dispatchers.IO

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
            val ingredients = ingredientsList.collectAsLazyPagingItems(
                defaultDispatcher
            )

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
                            IngredientCard(ingredient = it) {
                                // TODO: wire up add ingredient to recipe
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
        }
    }
}

/**
 * Displays ingredient category on simple card UI
 * @param category
 * @param onClickCategoryCard lambda to update ingredient query with
 * selected category
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun IngredientCategoryCard(
    category: IngredientCategory,
    onClickCategoryCard: (String) -> Unit,
) {
    Card(
        onClick = { onClickCategoryCard(category.categoryName) },
        elevation = CardDefaults.cardElevation(4.dp),
        modifier = Modifier
            .fillMaxWidth(1f)
            .padding(8.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth(1f)
                .align(Alignment.CenterHorizontally)
                .padding(8.dp)
        ) {
            Text(
                text = stringResource(id = category.displayLabelRes()),
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth(1f),
                textAlign = TextAlign.Center
            )
        }
    }
}

/**
 * Simple nav bar that allows for easy back navigation
 * @param onClickBackButton lambda for back navigation
 */
@Composable
fun SelectIngredientNavBar(
    onClickBackButton: () -> Unit,
) {
    NavigationBar(
        containerColor = MaterialTheme.colorScheme.primaryContainer,
        contentColor = MaterialTheme.colorScheme.onPrimaryContainer
    ) {
        NavigationBarItem(
            selected = false,
            onClick = onClickBackButton,
            icon = {
                Icon(
                    painter = painterResource(id = R.drawable.back_arrow), 
                    contentDescription = stringResource(id = R.string.back_arrow_description),
                )
            },
            label = {
                Text(
                    text = stringResource(
                        id = R.string.select_ingredient_back_button_label
                    ),
                )
            },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = MaterialTheme.colorScheme.onPrimaryContainer,
                selectedTextColor = MaterialTheme.colorScheme.onPrimaryContainer,
                indicatorColor = MaterialTheme.colorScheme.primaryContainer,
            )
        )
    }
}