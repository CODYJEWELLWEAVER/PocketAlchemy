package com.android.pocketalchemy.editrecipe

import android.util.Log
import androidx.lifecycle.ViewModel
import com.android.pocketalchemy.firebase.AuthRepository
import com.android.pocketalchemy.model.Recipe
import com.android.pocketalchemy.model.RecipeRepository
import com.google.firebase.firestore.toObject
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

@HiltViewModel(assistedFactory = EditRecipeViewModel.EditRecipeViewModelFactory::class)
class EditRecipeViewModel @AssistedInject constructor(
    @Assisted var recipeId: String?,
    private val recipeRepository: RecipeRepository,
    private val authRepository: AuthRepository
) : ViewModel() {
    private var _recipe: MutableStateFlow<Recipe>
    private val userId: String?
        get() = authRepository.getUserIdString()

    init {
        val recipeDoc = recipeRepository.getRecipe(recipeId)

        if (recipeId == null) {
            // If creating a new document update id
            recipeId = recipeDoc.id
        }

        _recipe = MutableStateFlow(
            Recipe(
                recipeId = recipeDoc.id,
                userId = this.userId
            )
        )

        recipeDoc.get()
            .addOnSuccessListener { snapshot ->
                Log.d(TAG, "Successfully retrieved recipe snapshot... ")
                snapshot.toObject<Recipe>()?.let {
                    if (it.userId == this.userId) {
                        _recipe.value = it
                    }
                }
            }
            .addOnFailureListener {
                Log.w(TAG, "Cannot get recipe with id: $recipeId, ex: $it")
            }
    }

    fun getRecipe(): StateFlow<Recipe> {
        return _recipe.asStateFlow()
    }

    fun updateRecipe(onUpdate: (Recipe) -> (Recipe)) {
        _recipe.update { oldRecipe ->
            onUpdate(oldRecipe)
        }
    }

    fun saveRecipe() {
        recipeRepository.saveRecipe(_recipe.value)
    }

    @AssistedFactory
    interface EditRecipeViewModelFactory {
        fun create(recipeId: String?): EditRecipeViewModel
    }

    companion object {
        private const val TAG = "EditRecipeViewModel"
    }
}