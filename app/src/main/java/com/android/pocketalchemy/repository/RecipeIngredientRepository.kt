package com.android.pocketalchemy.repository

import android.util.Log
import com.android.pocketalchemy.firebase.FirestoreCollections.RECIPE_INGREDIENTS_COLLECTION
import com.android.pocketalchemy.model.RecipeIngredient
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.toObjects
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

private const val TAG = "RecipeIngredientRepository"

@ViewModelScoped
class RecipeIngredientRepository @Inject constructor(
    private val firestore: FirebaseFirestore
) {
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
    /**
     * Returns a list of ingredients for a given recipe object.
     */
    fun getRecipeIngredients(
        recipeId: String,
        onSuccess: (List<RecipeIngredient>) -> Unit
    ) {
        firestore.collection(RECIPE_INGREDIENTS_COLLECTION)
            .whereEqualTo(RecipeIngredient.RECIPE_ID_KEY, recipeId)
            .get()
            .addOnSuccessListener {
                val recipeIngredients = it.toObjects<RecipeIngredient>()
                onSuccess(recipeIngredients)
            }
            .addOnFailureListener {
                Log.d(TAG, it.toString())
            }
    }
}