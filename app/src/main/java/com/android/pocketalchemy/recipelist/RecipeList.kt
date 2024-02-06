package com.android.pocketalchemy.recipelist

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
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
    val recipeList: State<List<Recipe>>
        = recipeListViewModel.recipeList.collectAsState(initial = emptyList())

    if (recipeList.value.isNotEmpty()) {
        RecipeListColumn(recipeList.value, onNavigateToEditRecipe)
    } else if (recipeListViewModel.isLoading) {
        LoadingRecipesIndicator()
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

/**
 * Loading progress indicator.
 */
@Composable
fun LoadingRecipesIndicator() {
    Surface(
        color = MaterialTheme.colorScheme.background,
        contentColor = MaterialTheme.colorScheme.onBackground,
        modifier = Modifier.fillMaxSize(1f)
    ) {
        Box {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center)
            )
        }
    }
}
