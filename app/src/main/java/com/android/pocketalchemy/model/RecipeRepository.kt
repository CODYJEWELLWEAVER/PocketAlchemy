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

/**
 * Repository for accessing recipe collection.
 * @param firestore firestore instance
 * @param authRepository repository for authentication
 */
@ViewModelScoped
class RecipeRepository @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val authRepository: AuthRepository
) {

    /**
     * Returns a flow of current user's recipes.
     */
    fun getUserRecipeList(): Flow<List<Recipe>> {
        val user = authRepository.getUser()
        return firestore.collection(RECIPE_COLLECTION)
            .whereEqualTo(Recipe.USER_ID_FIELD, user.uid)
            .dataObjects<Recipe>()

    }

    /**
     * Returns a reference to document with given id or
     * creates a new document if recipeId is null.
     * @param recipeId recipeId to retrieve or null for document creation
     */
    fun getRecipe(recipeId: String?): DocumentReference {
        return if (recipeId == null) {
            // Creates new document ref
            firestore.collection(RECIPE_COLLECTION).document()
        } else {
            Log.d(TAG, recipeId)
            // Return doc
            firestore.collection(RECIPE_COLLECTION).document(recipeId)
        }
    }

    /**
     * Saves current recipe as document in recipe collection.
     * @param recipe Recipe object to insert into collection
     */
    fun insertRecipe(recipe: Recipe) {
        recipe.recipeId?.let { id ->
            firestore.collection(RECIPE_COLLECTION).document(id)
                .set(recipe)
                .addOnSuccessListener {
                    Log.i(TAG, "Insert recipe successful...")

                }
                .addOnFailureListener {
                    Log.w(TAG, "Could not insert recipe. Exception: $it")
                }
        }
    }
}
