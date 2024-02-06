package com.android.pocketalchemy.editrecipe

import com.android.pocketalchemy.model.Recipe
import com.android.pocketalchemy.model.RecipeIngredient

/**
 * Wrapper class for holding state of EditRecipeScreen
 * @param recipe Current state of recipe model
 * @param ingredients Current state of recipe ingredients
 */
data class EditRecipeUiState(
    val recipe: Recipe,
    val ingredients: List<RecipeIngredient>,
)