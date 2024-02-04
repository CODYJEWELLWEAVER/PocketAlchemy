package com.android.pocketalchemy.repository

import com.android.pocketalchemy.firebase.FirestoreCollections.RECIPE_INGREDIENTS_COLLECTION
import com.android.pocketalchemy.model.RecipeIngredient
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.toObjects
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

private const val TAG = "RecipeIngredientRepository"

@ViewModelScoped
class RecipeIngredientRepository @Inject constructor(
    private val firestore: FirebaseFirestore
) {
    /**
     * Returns a list of ingredients for a given recipe object.
     * @param recipeId
     */
    suspend fun getRecipeIngredients(
        recipeId: String
    ): List<RecipeIngredient> {
        return firestore.collection(RECIPE_INGREDIENTS_COLLECTION)
            .whereEqualTo(RecipeIngredient.RECIPE_ID_KEY, recipeId)
            .get()
            .await()
            .toObjects<RecipeIngredient>()
    }
}