package com.android.pocketalchemy.model

import com.android.pocketalchemy.firebase.AuthRepository
import com.android.pocketalchemy.firebase.FirestoreCollections.RECIPE_COLLECTION
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.dataObjects
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.components.ViewModelComponent
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

private const val TAG = "RecipeRepository"

@Module
@InstallIn(ActivityComponent::class, ViewModelComponent::class)
class RecipeRepository @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val authRepository: AuthRepository
) {

    fun getUserRecipes(): Flow<List<Recipe>> {
        val user = authRepository.getUser()
        return firestore.collection(RECIPE_COLLECTION)
            .whereEqualTo(Recipe.USER_ID_FIELD, user.uid)
            .dataObjects<Recipe>()

    }

    fun insertUserRecipe(recipe: Recipe) {
        firestore.collection(RECIPE_COLLECTION).add(recipe)
    }
}
