package com.android.pocketalchemy.recipelist

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.android.pocketalchemy.model.Recipe
import com.android.pocketalchemy.ui.common.LoadingIndicator

/**
 * Lazy Column for displaying lists of recipe cards.
 * @param onNavigateToEditRecipe navigation callback for editing recipe
 * @param recipeListViewModel view model
 */
@Composable
fun RecipeList(
    onNavigateToEditRecipe: (String?) -> Unit,
    recipeListViewModel: RecipeListViewModel = hiltViewModel<RecipeListViewModel>()
) {
    val recipeListUiState by recipeListViewModel.recipeListUiState.collectAsState()
    val isLoading = recipeListUiState.isLoading
    val recipeList by recipeListUiState.recipeList.collectAsState(initial = emptyList())

    if (!isLoading) {
        RecipeListColumn(recipeList, onNavigateToEditRecipe)
    } else {
        LoadingIndicator()
    }
}

/**
 * Scrollable column of user's recipes.
 * @param recipeList current user's recipe list
 * @param onNavigateToEditRecipe callback to open clicked recipe
 */
@Composable
fun RecipeListColumn(
    recipeList: List<Recipe>,
    onNavigateToEditRecipe: (String?) -> Unit,
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp),
        contentPadding = PaddingValues(8.dp)
    ) {
        items(recipeList) {
            Box(
                modifier = Modifier.padding(vertical = 8.dp)
            ) {
                RecipeListCard(recipe = it, onNavigateToEditRecipe)
            }
        }
    }
}
