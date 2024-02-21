package com.android.pocketalchemy.editrecipe

import com.android.pocketalchemy.model.Recipe
import com.android.pocketalchemy.model.RecipeIngredient

/**
 * Wrapper class for holding state of EditRecipeScreen
 * @param recipe Current state of recipe model
 * @param ingredients Current state of recipe ingredients
 * @param showSelectIngredientPopUp Controls ingredient selector pop up visibility
 * @param isLoading true if fetching recipe data
 */
data class EditRecipeUiState(
    val recipe: Recipe,
    val ingredients: List<RecipeIngredient>,
    val showSelectIngredientPopUp: Boolean,
    val isLoading: Boolean,
)