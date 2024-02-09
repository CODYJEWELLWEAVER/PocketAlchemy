package com.android.pocketalchemy.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.android.pocketalchemy.model.Ingredient
import com.android.pocketalchemy.repository.IngredientRepository
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.toObjects

private const val TAG = "IngredientPagingSource"

class IngredientPagingSource(
    private val ingredientRepository: IngredientRepository,
    private val query: Query
): PagingSource<DocumentSnapshot, Ingredient>() {
    override suspend fun load(params: LoadParams<DocumentSnapshot>): LoadResult<DocumentSnapshot, Ingredient> {
        return try {
            // if params.key is null, loads first result
            val firstDocument = params.key
            val ingredientsSnapshot = ingredientRepository.getIngredientsSnapshot(query, firstDocument)
            val nextKey = if (ingredientsSnapshot.isEmpty) {
                null
            } else {
                ingredientsSnapshot.documents[ingredientsSnapshot.size() - 1]
            }
            LoadResult.Page(
                data = ingredientsSnapshot.toObjects<Ingredient>(),
                prevKey = null,
                nextKey = nextKey
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<DocumentSnapshot, Ingredient>): DocumentSnapshot? {
        return null
    }
}