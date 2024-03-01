package com.android.pocketalchemy.recipelist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.pocketalchemy.repository.RecipeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * View Model For Recipe List Screen
 * @param recipeRepository
 */
@HiltViewModel
class RecipeListViewModel @Inject constructor(
    private val recipeRepository: RecipeRepository,
) : ViewModel() {

    // UI State
    private val _recipeListUiState = MutableStateFlow(
        RecipeListUiState(
            isLoading = false,
            recipeList = emptyFlow()
        )
    )

    // Public accessor for UI state
    val recipeListUiState: StateFlow<RecipeListUiState>
        get() = _recipeListUiState.asStateFlow()

    init {
        setIsLoading(true)
        getRecipeList()
        setIsLoading(false)
    }

    private fun setIsLoading(value: Boolean) {
        _recipeListUiState.update {
            it.copy(isLoading = value)
        }
    }

    private fun getRecipeList() {
        viewModelScope.launch {
            _recipeListUiState.update {
                it.copy(recipeList = recipeRepository.getUserRecipeList())
            }
        }
    }
}