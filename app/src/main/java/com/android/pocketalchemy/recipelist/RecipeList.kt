package com.android.pocketalchemy.recipelist

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.android.pocketalchemy.model.Recipe

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
    val recipes: State<List<Recipe>>
        = recipeListViewModel.recipes.collectAsState(initial = emptyList())
    
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp),
        contentPadding = PaddingValues(8.dp)
    ) {
        if (recipes.value.isEmpty()) {
            // TODO: Show help card when list is empty
        } else {
            items(recipes.value) {
                Box(
                    modifier = Modifier.padding(vertical = 8.dp)
                ) {
                    RecipeListCard(recipe = it, onNavigateToEditRecipe)
                }
            }
        }
    }
}