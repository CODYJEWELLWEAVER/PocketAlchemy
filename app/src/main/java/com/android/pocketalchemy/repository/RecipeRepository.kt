package com.android.pocketalchemy.repository

import android.util.Log
import com.android.pocketalchemy.firebase.FirestoreCollections.RECIPE_COLLECTION
import com.android.pocketalchemy.model.Recipe
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.dataObjects
import com.google.firebase.firestore.toObject
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.tasks.await
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
    private val ioDispatcher = Dispatchers.IO
    /**
     * Returns a flow of current user's recipes, if
     * user is not logged in returns empty flow.
     */
    fun getUserRecipeList(): Flow<List<Recipe>>  {
        val user = authRepository.getUser()
        return if (user != null) {
            firestore.collection(RECIPE_COLLECTION)
                .whereEqualTo(Recipe.USER_ID_FIELD, user.uid)
                .dataObjects<Recipe>()
        } else {
            emptyFlow()
        }
    }

    /**
     * Returns a reference to document with given id or
     * creates a new document if recipeId is null.
     * @param recipeId recipeId to retrieve or null for document creation
     */
    fun getRecipeDocRef(recipeId: String?): DocumentReference {
        Log.d(TAG, "get recipe with $recipeId")
        return if (recipeId == null) {
            // Creates new document ref
            firestore.collection(RECIPE_COLLECTION).document()
        } else {
            // Return existing document ref
            firestore.collection(RECIPE_COLLECTION).document("$recipeId")
        }
    }

    /**
     * Retrieves Recipe model object for a doc ref.
     * @param recipeDoc
     */
    suspend fun getRecipe(
        recipeDoc: DocumentReference
    ): Recipe? {
        return recipeDoc.get()
            .await()
            .toObject<Recipe>()
    }

    /**
     * Saves current recipe as document in recipe collection.
     * @param recipe Recipe object to insert into collection
     */
    fun setRecipe(recipe: Recipe) {
        Log.d(TAG, recipe.id.toString())
        recipe.id?.let { id ->
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