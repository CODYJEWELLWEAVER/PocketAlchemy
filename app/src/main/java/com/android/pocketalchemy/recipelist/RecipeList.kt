package com.android.pocketalchemy.recipelist

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.android.pocketalchemy.R
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

    if (recipes.value.isNotEmpty()) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
            contentPadding = PaddingValues(8.dp)
        ) {
            items(recipes.value) {
                Box(
                    modifier = Modifier.padding(vertical = 8.dp)
                ) {
                    RecipeListCard(recipe = it, onNavigateToEditRecipe)
                }
            }
        }
    } else {
        Surface(
            color = MaterialTheme.colorScheme.primaryContainer,
            contentColor = MaterialTheme.colorScheme.onPrimaryContainer,
            onClick = { onNavigateToEditRecipe(null) }, // navigates to new recipe
            modifier = Modifier.fillMaxSize(1f)
        ) {
            Box {
                Text(
                    text = stringResource(id = R.string.empty_recipe_list_prompt),
                    modifier = Modifier.align(Alignment.Center),
                    style = MaterialTheme.typography.headlineLarge,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                )
            }
        }
    }
    

}