package com.android.pocketalchemy.recipelist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.pocketalchemy.model.Recipe
import com.android.pocketalchemy.repository.RecipeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * View Model For Recipe List Screen
 * @param recipeRepository Repository for recipe collection
 */
@HiltViewModel
class RecipeListViewModel @Inject constructor(
    private val recipeRepository: RecipeRepository,
) : ViewModel() {

    private var _isLoading = false

    val isLoading
        get() = _isLoading

    private var _recipeList: StateFlow<List<Recipe>>
        = MutableStateFlow(emptyList())

    val recipeList: StateFlow<List<Recipe>>
        get() {
            return _recipeList
        }

    init {
        _isLoading = true
        getRecipeList()
    }

    private fun getRecipeList() {
        viewModelScope.launch {
            _recipeList = recipeRepository.getUserRecipeList()
                .stateIn(this)
            _isLoading = false
        }
    }
}