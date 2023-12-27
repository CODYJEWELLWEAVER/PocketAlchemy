package com.android.pocketalchemy.editrecipe

import android.util.Log
import androidx.lifecycle.ViewModel
import com.android.pocketalchemy.model.Recipe
import com.android.pocketalchemy.model.RecipeRepository
import com.google.firebase.firestore.toObject
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class EditRecipeViewModel @Inject constructor(
    private val recipeRepository: RecipeRepository
) : ViewModel() {

    fun getRecipeObject(recipeDocumentId: String?): Recipe {
        val recipeDoc = recipeRepository.getRecipe(recipeDocumentId)

        var recipeObject = Recipe(
            recipeDoc.id,
        )

        recipeDoc.get()
            .addOnSuccessListener { snapshot ->
                Log.d(TAG, "Successfully retrieved recipe snapshot... ")
                snapshot.toObject<Recipe>()?.let {
                    recipeObject = it
                }
            }
            .addOnFailureListener {
                Log.w(TAG, "Cannot get recipe with id: $recipeDocumentId, ex: $it")
            }

        return recipeObject
    }



    companion object {
        private const val TAG = "EditRecipeViewModel"
    }
}