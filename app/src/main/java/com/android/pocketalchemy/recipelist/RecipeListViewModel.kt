package com.android.pocketalchemy.recipelist

import androidx.lifecycle.ViewModel
import com.android.pocketalchemy.model.Recipe
import com.android.pocketalchemy.repository.RecipeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * View Model For Recipe List Screen
 * @param recipeRepository Repository for recipe collection
 */
@HiltViewModel
class RecipeListViewModel @Inject constructor(
    private val recipeRepository: RecipeRepository,
) : ViewModel() {
    /**
     * Retrieves a flow of current users recipes
     */
    val recipes: Flow<List<Recipe>>
        get() = recipeRepository.getUserRecipeList()
}