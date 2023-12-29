package com.android.pocketalchemy.editrecipe

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.pocketalchemy.firebase.AuthRepository
import com.android.pocketalchemy.model.Recipe
import com.android.pocketalchemy.model.RecipeRepository
import com.google.firebase.firestore.toObject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel for EditRecipeScreen
 */
@HiltViewModel
class EditRecipeViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val recipeRepository: RecipeRepository,
    private val authRepository: AuthRepository
) : ViewModel() {
    private val recipeId: String?
        get() = savedStateHandle[EDIT_RECIPE_ID_KEY]

    private var _recipe: MutableStateFlow<Recipe> = MutableStateFlow(Recipe())
    private val userId: String?
        get() = authRepository.getUserIdString()

    fun setRecipeId(recipeId: String?) {
        val recipeDoc = recipeRepository.getRecipe(recipeId)
        savedStateHandle[EDIT_RECIPE_ID_KEY] = recipeDoc.id
        _recipe.update {
            it.copy(userId = userId, recipeId = recipeId)
        }
    }

    fun clearRecipeId() {
        savedStateHandle[EDIT_RECIPE_ID_KEY] = null
        updateRecipe {
            Recipe(userId = userId)
        }
    }

    fun getRecipe(): StateFlow<Recipe> {
        if (this.recipeId != null) {
            val recipeDoc = recipeRepository.getRecipe(recipeId)

            recipeDoc.get()
                .addOnSuccessListener { snapshot ->
                    Log.d(TAG, "Successfully retrieved recipe snapshot... ")
                    snapshot.toObject<Recipe>().let { newRecipe ->
                        _recipe.update { oldRecipe ->
                            if (newRecipe?.recipeId != null) {
                                newRecipe
                            } else {
                                oldRecipe
                            }
                        }
                    }
                }
                .addOnFailureListener {
                    Log.w(TAG, "Cannot get recipe with id: $recipeId, ex: $it")
                }
        }

        return _recipe.asStateFlow()
    }

    fun updateRecipe(onUpdate: (Recipe) -> (Recipe)) {
        _recipe.update { oldRecipe ->
            onUpdate(oldRecipe)
        }
    }

    /**
     * Saves recipe and then clears recipe id.
     */
    fun saveRecipe() {
        // TODO: Check no required fields are empty!!!
        viewModelScope.launch {
            _recipe.collectLatest {
                recipeRepository.insertRecipe(it.copy(recipeId = recipeId))
            }
        }

        this.clearRecipeId()
    }

    companion object {
        private const val TAG = "EditRecipeViewModel"
        private const val EDIT_RECIPE_ID_KEY = "edit-recipe-id-key"
    }
}