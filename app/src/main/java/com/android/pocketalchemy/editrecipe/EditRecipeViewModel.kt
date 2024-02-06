package com.android.pocketalchemy.editrecipe

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.pocketalchemy.model.Recipe
import com.android.pocketalchemy.model.RecipeIngredient
import com.android.pocketalchemy.repository.AuthRepository
import com.android.pocketalchemy.repository.RecipeIngredientRepository
import com.android.pocketalchemy.repository.RecipeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val TAG = "EditRecipeViewModel"

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
    private var _isLoading = false
    val isLoading
        get() = _isLoading
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
     * @param recipeId used to retrieve recipe document
     */
    fun setRecipe(recipeId: String?) {
        viewModelScope.launch {
            _isLoading = true
            val recipeDocRef = recipeRepository.getRecipeDocRef(recipeId)
            savedStateHandle[EDIT_RECIPE_ID_KEY] = recipeDocRef.id

            // sets initial recipe details
            val recipe = recipeRepository.getRecipe(recipeDocRef)

            // sets initial ingredient list
            val ingredients = recipeIngredientRepository.getRecipeIngredients(recipeId.toString())

            updateUiState(recipe, ingredients)
            _isLoading = false
        }
    }

    /**
     * Updates the state of the recipe and its ingredients being
     * edited. Has no effect if both parameters are null.
     * @param recipe new recipe to update UI state with
     * @param ingredients new ingredients to update UI state with
     */
    fun updateUiState(
        recipe: Recipe? = null,
        ingredients: List<RecipeIngredient>? = null,
    ) {
        viewModelScope.launch {
            if (recipe != null && ingredients != null) {
                _editRecipeUiState.update {
                    it.copy(
                        recipe = recipe,
                        ingredients = ingredients
                    )
                }
            } else {
                recipe?.let { newRecipe ->
                    _editRecipeUiState.update {
                        it.copy(recipe = newRecipe)
                    }
                }

                ingredients?.let { newIngredients ->
                    _editRecipeUiState.update {
                        it.copy(ingredients = newIngredients)
                    }
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

    /**
     * Adds a given RecipeIngredient to a recipe, if the recipe already contains
     * a RecipeIngredient with the same ingredientId, the gram weights are summed
     * and the ingredient list is updated.
     * @param recipeIngredient ingredient being added
     */
    fun addRecipeIngredient(recipeIngredient: RecipeIngredient) {
        viewModelScope.launch { // TODO: Move to default dispatcher
            val recipeIngredients = _editRecipeUiState.value.ingredients
            val ingredientIndex = recipeIngredients.indexOfFirst {
                it.ingredientId == recipeIngredient.ingredientId
            }
            val newRecipeIngredients = if (ingredientIndex == NOT_FOUND) {
                recipeIngredients + recipeIngredient
            }
            else {
                recipeIngredients.mapIndexed { i: Int, ingredient: RecipeIngredient ->
                    if (i == ingredientIndex) {
                        ingredient.copy(
                            gramWeight = ingredient.gramWeight + recipeIngredient.gramWeight
                        )
                    } else {
                        ingredient
                    }
                }
            }
            updateUiState(
                ingredients = newRecipeIngredients
            )
        }
    }

    /**
     * Removes RecipeIngredient and tracks it if it needs to be
     * deleted from firestore on call to [saveRecipe]
     * @param recipeIngredient ingredient being removed
     */
    fun removeRecipeIngredient(recipeIngredient: RecipeIngredient) {
        viewModelScope.launch {
            val recipeIngredients = _editRecipeUiState.value.ingredients
            updateUiState(
                ingredients = recipeIngredients.filter {
                    it.ingredientId != recipeIngredient.id
                }
            )
            if (recipeIngredient.id != null) {
                // Tracks ingredients that need to be deleted
                // from firestore on recipe save
                recipeIngredientsToDelete.add(recipeIngredient)
            }
        }
    }

    /**
     * Saves recipe and then clears recipe id.
     */
    fun saveRecipe() {
        // TODO: Check no required fields are empty!!!
        viewModelScope.launch {
            recipeRepository.setRecipe(
                // Recipe id is auto filled with doc id so must copy auto
                // id before setting.
                _editRecipeUiState.value.recipe.copy(id = recipeId)
            )
            clearRecipeId()
        }
    }

    companion object {
        private const val NOT_FOUND = -1
        private const val EDIT_RECIPE_ID_KEY = "edit-recipe-id-key"
    }
}