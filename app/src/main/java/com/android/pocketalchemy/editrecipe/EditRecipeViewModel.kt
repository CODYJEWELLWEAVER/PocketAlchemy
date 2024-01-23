package com.android.pocketalchemy.editrecipe

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.pocketalchemy.model.Recipe
import com.android.pocketalchemy.model.RecipeIngredient
import com.android.pocketalchemy.repository.AuthRepository
import com.android.pocketalchemy.repository.RecipeIngredientRepository
import com.android.pocketalchemy.repository.RecipeRepository
import com.google.firebase.firestore.toObject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
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
    private val recipeIngredientRepository: RecipeIngredientRepository,
    private val authRepository: AuthRepository,
) : ViewModel() {
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
    /**
     * Retrieves recipe id from SavedStateHandle
     */
    private val recipeId: String?
        get() = savedStateHandle[EDIT_RECIPE_ID_KEY]

    /**
     * Retrieves auth user id
     */
    private val userId: String
        get() = authRepository.getUserIdString() ?: ""

    /** ui state model for EditRecipeScreen */
    private var _editRecipeUiState: MutableStateFlow<EditRecipeUiState>
        = MutableStateFlow(EditRecipeUiState(
            Recipe(id = recipeId, userId = userId), emptyList())
        )

    /** public accessor for ui state model */
    val editRecipeUiState: StateFlow<EditRecipeUiState>
        get() = _editRecipeUiState.asStateFlow()

    /**
     * List of ingredients to be deleted from firestore on save.
     * Ingredients that are added and then removed before a
     * call to [saveRecipe] are not tracked as there is no
     * need to delete them from firestore.
     */
    private val recipeIngredientsToDelete = mutableListOf<RecipeIngredient>()

    /**
     * Initializes view model with recipe from recipeId.
     * Has no effect if already set.
     * @param recipeId used to retrieve recipe document
     */
    fun setRecipe(recipeId: String?) {
        viewModelScope.launch(
            ioDispatcher
        ) {
            val recipeDoc = recipeRepository.getRecipe(recipeId)
            savedStateHandle[EDIT_RECIPE_ID_KEY] = recipeDoc.id

            recipeDoc.get()
                .addOnSuccessListener { snapshot ->
                    snapshot.toObject<Recipe>()?.let { recipeSnapshot ->
                        updateUiState(recipe = recipeSnapshot)
                    }
                }
                .addOnFailureListener {
                    Log.w(TAG, "Cannot get recipe with id: $recipeId, ex: $it")
                }

            // sets initial ingredient list
            recipeIngredientRepository.getRecipeIngredients(recipeId.toString()) {
                updateUiState(recipeIngredients = it)
            }
        }
    }

    /**
     * updates EditRecipeUiState
     */
    fun updateUiState(
        recipe: Recipe? = null,
        recipeIngredients: List<RecipeIngredient>? = null
    ) {
        viewModelScope.launch {
            recipe?.let { newRecipe ->
                _editRecipeUiState.update {
                    it.copy(recipe = newRecipe)
                }
            }

            recipeIngredients?.let { newRecipeIngredients ->
                _editRecipeUiState.update {
                    it.copy(ingredients = newRecipeIngredients)
                }
            }
        }
    }

    /**
     * Resets recipeId used by view model
     */
    fun clearRecipeId() {
        savedStateHandle[EDIT_RECIPE_ID_KEY] = null
    }

    fun addRecipeIngredient(recipeIngredient: RecipeIngredient) {
        viewModelScope.launch {
            val recipeIngredients = _editRecipeUiState.value.ingredients
            updateUiState(
                recipeIngredients = recipeIngredients + recipeIngredient
            )
        }
    }

    fun removeRecipeIngredient(recipeIngredient: RecipeIngredient) {
        viewModelScope.launch {
            val recipeIngredients = _editRecipeUiState.value.ingredients
            updateUiState(
                recipeIngredients = recipeIngredients.filter {
                    it.ingredientId != recipeIngredient.id
                }
            )
        }
    }

    /**
     * Saves recipe and then clears recipe id.
     */
    fun saveRecipe() {
        // TODO: Check no required fields are empty!!!
        viewModelScope.launch(
            ioDispatcher
        ) {
            recipeRepository.setRecipe(_editRecipeUiState.value.recipe)
            clearRecipeId()
        }
    }

    companion object {
        private const val TAG = "EditRecipeViewModel"
        private const val EDIT_RECIPE_ID_KEY = "edit-recipe-id-key"
    }
}