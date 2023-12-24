package com.android.pocketalchemy.recipelist

import androidx.lifecycle.ViewModel
import com.android.pocketalchemy.model.Recipe
import com.android.pocketalchemy.model.RecipeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class RecipeListViewModel @Inject constructor(
    val recipeRepository: RecipeRepository
) : ViewModel() {

    val recipes: Flow<List<Recipe>>
        get() = recipeRepository.getUserRecipes()
}




