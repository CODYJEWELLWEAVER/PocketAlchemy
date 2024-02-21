package com.android.pocketalchemy.editrecipe.selectingredient

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import androidx.hilt.navigation.compose.hiltViewModel
import com.android.pocketalchemy.R
import com.android.pocketalchemy.model.IngredientCategory
import com.android.pocketalchemy.model.displayLabelRes
import com.android.pocketalchemy.ui.common.PaTopAppBar

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

                LazyColumn(
                    modifier = Modifier.padding(8.dp),
                ) {
                    items(IngredientCategory.entries) {
                        IngredientCategoryCard(category = it) { categoryName ->
                            selectIngredientViewModel.updateCategory(categoryName)
                        }
                    }
                }

                if (selectedCategory != null) {
                    SelectIngredient(selectIngredientViewModel) {
                        selectIngredientViewModel.updateCategory(null)
                    }
                }

                BackHandler(
                    enabled = (selectedCategory != null)
                ) {
                    selectIngredientViewModel.updateCategory(null)
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