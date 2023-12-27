package com.android.pocketalchemy.model

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

    fun saveRecipe(recipe: Recipe) {
        firestore.collection(RECIPE_COLLECTION).document(recipe.recipeId)
            .set(recipe)
    }
}
