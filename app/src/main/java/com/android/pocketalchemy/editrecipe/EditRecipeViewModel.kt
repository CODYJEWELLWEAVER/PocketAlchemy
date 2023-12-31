package com.android.pocketalchemy.editrecipe

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.pocketalchemy.repository.AuthRepository
import com.android.pocketalchemy.model.Recipe
import com.android.pocketalchemy.repository.RecipeRepository
import com.google.firebase.firestore.toObject
import dagger.hilt.android.lifecycle.HiltViewModel
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
    /**
     * Retrieves recipe id from SavedStateHandle
     */
    private val recipeId: String?
        get() = savedStateHandle[EDIT_RECIPE_ID_KEY]

    /**
     * Retrieves auth user id
     */
    private val userId: String?
        get() = authRepository.getUserIdString()

    /**
     * ui state model for EditRecipeScreen
     */
    private var _recipeState: MutableState<Recipe> = mutableStateOf(Recipe(userId = userId, id = recipeId))
    /**
     * public accessor for ui state model
     */
    val recipeState: State<Recipe>
        get() = _recipeState

    /**
     * Initializes view model with recipe from recipeId.
     * Has no effect if already set.
     * @param recipeId used to retrieve recipe document
     */
    fun setRecipeId(recipeId: String?) {
        // no effect if already set
        if (this.recipeId == null) {
            val recipeDoc = recipeRepository.getRecipe(recipeId)
            savedStateHandle[EDIT_RECIPE_ID_KEY] = recipeDoc.id
            getRecipeSnapshot()
        }
    }

    /**
     * Resets recipeId used by view model
     */
    fun clearRecipeId() {
        savedStateHandle[EDIT_RECIPE_ID_KEY] = null
    }

    /**
     * Sends request to get recipe document and
     * adds listener to update _recipe with document
     * content when request completes.
     */
    private fun getRecipeSnapshot() {
        Log.d(TAG, "getRecipeSnapshot")
        val recipeDoc = recipeRepository.getRecipe(recipeId)

        recipeDoc.get()
            .addOnSuccessListener { snapshot ->
                snapshot.toObject<Recipe>()?.let {
                    updateRecipeState(it)
                }
            }
            .addOnFailureListener {
                Log.w(TAG, "Cannot get recipe with id: $recipeId, ex: $it")
            }
    }

    /**
     * Updates ui state model
     */
    fun updateRecipeState(recipe: Recipe) {
        _recipeState.value = recipe.copy(id = recipeId)
    }

    /**
     * Saves recipe and then clears recipe id.
     */
    fun saveRecipe() {
        // TODO: Check no required fields are empty!!!
        viewModelScope.launch {
            recipeRepository.insertRecipe(recipeState.value)
            clearRecipeId()
        }
    }

    companion object {
        private const val TAG = "EditRecipeViewModel"
        private const val EDIT_RECIPE_ID_KEY = "edit-recipe-id-key"
    }
}