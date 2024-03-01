package com.android.pocketalchemy.recipelist

import com.android.pocketalchemy.model.Recipe
import kotlinx.coroutines.flow.Flow

/**
 * Wrapper for recipe list UI state
 * @param recipeList user's recipe list 
 * @param isLoading true if fetching recipe list data
 */
data class RecipeListUiState(
    val recipeList: Flow<List<Recipe>>,
    val isLoading: Boolean
)