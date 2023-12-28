package com.android.pocketalchemy.model

import android.util.Log
import com.android.pocketalchemy.firebase.AuthRepository
import com.android.pocketalchemy.firebase.FirestoreCollections.RECIPE_COLLECTION
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.dataObjects
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

private const val TAG = "RecipeRepository"

@ViewModelScoped
class RecipeRepository @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val authRepository: AuthRepository
) {

    fun getUserRecipeList(): Flow<List<Recipe>> {
        val user = authRepository.getUser()
        return firestore.collection(RECIPE_COLLECTION)
            .whereEqualTo(Recipe.USER_ID_FIELD, user.uid)
            .dataObjects<Recipe>()

    }

    fun insertUserRecipe(recipe: Recipe) {
        firestore.collection(RECIPE_COLLECTION).add(recipe)
    }

    fun getRecipe(recipeDocumentId: String?): DocumentReference {
        return if (recipeDocumentId == null) {
            // Creates new document ref
            firestore.collection(RECIPE_COLLECTION).document()
        } else {
            // Return snapshot of current doc
            firestore.collection(RECIPE_COLLECTION).document(recipeDocumentId)
        }
    }

    /**
     * Saves current recipe as document in recipe collection.
     */
    fun saveRecipe(recipe: Recipe) {
        recipe.recipeId?.let {
            firestore.collection(RECIPE_COLLECTION).document(it)
                .set(recipe)
                .addOnSuccessListener {
                    Log.i(TAG, "Save recipe successful...")

                }
                .addOnFailureListener {
                    Log.w(TAG, "Could not save recipe. Exception: $it")
                }
        }
    }
}
