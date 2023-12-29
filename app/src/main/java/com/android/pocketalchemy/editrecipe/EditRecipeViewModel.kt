package com.android.pocketalchemy.editrecipe

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.android.pocketalchemy.firebase.AuthRepository
import com.android.pocketalchemy.model.Recipe
import com.android.pocketalchemy.model.RecipeRepository
import com.google.firebase.firestore.toObject
import dagger.hilt.android.lifecycle.HiltViewModel
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

    var _recipe: MutableState<Recipe> = mutableStateOf(Recipe(userId = userId, recipeId = recipeId))
    val recipe: State<Recipe>
        get() = _recipe

    private val userId: String?
        get() = authRepository.getUserIdString()

    fun setRecipeId(recipeId: String?) {
        val recipeDoc = recipeRepository.getRecipe(recipeId)
        savedStateHandle[EDIT_RECIPE_ID_KEY] = recipeDoc.id
        getRecipeSnapshot()
    }

    fun clearRecipeId() {
        savedStateHandle[EDIT_RECIPE_ID_KEY] = null
    }

    private fun getRecipeSnapshot() {
        val recipeDoc = recipeRepository.getRecipe(recipeId)

        recipeDoc.get()
            .addOnSuccessListener { snapshot ->
                _recipe.value = snapshot.toObject<Recipe>() ?: Recipe(userId = userId, recipeId = recipeId)
            }
            .addOnFailureListener {
                Log.w(TAG, "Cannot get recipe with id: $recipeId, ex: $it")
            }
    }

    fun updateRecipeState(recipe: Recipe) {
        _recipe.value = recipe
    }

    /**
     * Saves recipe and then clears recipe id..
     */
    fun saveRecipe(recipe: Recipe) {
        // TODO: Check no required fields are empty!!!
        recipeRepository.insertRecipe(recipe)

        this.clearRecipeId()
    }

    companion object {
        private const val TAG = "EditRecipeViewModel"
        private const val EDIT_RECIPE_ID_KEY = "edit-recipe-id-key"
    }
}