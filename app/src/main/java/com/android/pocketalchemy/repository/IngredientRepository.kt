package com.android.pocketalchemy.repository

import com.android.pocketalchemy.firebase.FirestoreCollections.INGREDIENT_COLLECTION
import com.android.pocketalchemy.paging.PageSizes.INGREDIENT_PAGE_SIZE
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.QuerySnapshot
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

@ViewModelScoped
class IngredientRepository @Inject constructor(
    private val firestore: FirebaseFirestore
)  {
    /**
     * Gets list of ingredients
     */
    suspend fun getIngredientsSnapshot(
        query: Query,
        firstDocument: DocumentSnapshot?,
    ): QuerySnapshot {
        var newQuery = query

        if (firstDocument != null) {
            newQuery = query.startAfter(firstDocument)
        }

        return newQuery
            .limit(INGREDIENT_PAGE_SIZE.toLong())
            .get()
            .await()
    }

    fun getIngredientCollectionRef(): CollectionReference {
        return firestore.collection(INGREDIENT_COLLECTION)
    }
}