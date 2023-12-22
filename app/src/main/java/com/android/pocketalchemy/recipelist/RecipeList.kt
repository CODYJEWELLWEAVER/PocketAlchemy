package com.android.pocketalchemy.recipelist

import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.android.pocketalchemy.ingredient.Ingredient
import com.android.pocketalchemy.recipe.Recipe
import java.time.LocalDateTime

@Composable
@Preview
fun RecipeList(
    recipeListViewModel: RecipeListViewModel = viewModel()
) {
    val ingredientList = mutableListOf<Ingredient>()
    ingredientList.add(Ingredient("Carrot"))
    ingredientList.add(Ingredient("Ginger"))
    ingredientList.add(Ingredient("Lemon"))
    ingredientList.add(Ingredient("Chicken Broth"))
    val recipe = Recipe(
        title = "Recipe Test",
        subtitle = "A good recipe.",
        kcalPerServing = 300U,
        date = LocalDateTime.now(),
        categories = emptyList(),
        ingredients = ingredientList,
        description = "This recipe is a test for a card appearing in a list of other recipes."
    )
    LazyColumn(
        modifier = Modifier
            .fillMaxHeight(1f)
            .padding(16.dp)
    ) {
        item {
            RecipeListCard(recipe = recipe, onClick = {})
        }
    }
}