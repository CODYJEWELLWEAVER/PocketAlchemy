package com.android.pocketalchemy.editrecipe

import com.android.pocketalchemy.model.Recipe
import com.android.pocketalchemy.model.RecipeIngredient

/**
 * Wrapper class for holding state of EditRecipeScreen
 */
data class EditRecipeUiState(
    var recipe: Recipe,
    val ingredients: List<RecipeIngredient>,
)