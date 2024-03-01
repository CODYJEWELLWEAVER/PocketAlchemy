package com.android.pocketalchemy.editrecipe.selectingredient

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.android.pocketalchemy.firebase.FirestoreIngredientCategory
import com.android.pocketalchemy.model.Ingredient
import com.android.pocketalchemy.model.IngredientCategory
import com.android.pocketalchemy.paging.IngredientPagingSource
import com.android.pocketalchemy.paging.PageSizes.INGREDIENT_PAGE_SIZE
import com.android.pocketalchemy.paging.PageSizes.INGREDIENT_PREFETCH_DISTANCE
import com.android.pocketalchemy.repository.IngredientRepository
import com.google.firebase.firestore.Query
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val TAG = "SelectIngredientViewModel"

@HiltViewModel
class SelectIngredientViewModel @Inject constructor(
    private val ingredientRepository: IngredientRepository,
): ViewModel() {
    private val defaultDispatcher = Dispatchers.Default
    private var _selectedCategory: IngredientCategory = IngredientCategory.ALL

    private val defaultQuery
        get() = ingredientRepository.getIngredientCollectionRef()
                    .orderBy(Ingredient.DESCRIPTION_KEY, Query.Direction.ASCENDING)

    private val _ingredientPagingState = MutableStateFlow(
        // Set initial query
        getIngredientsPagingFlow(
            defaultQuery
        )
    )

    val ingredientPagingState: StateFlow<Flow<PagingData<Ingredient>>>
        get () = _ingredientPagingState.asStateFlow()

    /**
     * Updates paging flow with new query
     * @param query new query for paging data
     */
    private fun getIngredientsPagingFlow(
        query: Query
    ): Flow<PagingData<Ingredient>> {
       return Pager(
            PagingConfig(
                pageSize = INGREDIENT_PAGE_SIZE,
                prefetchDistance = INGREDIENT_PREFETCH_DISTANCE,
            )
        ) {
            IngredientPagingSource(
                ingredientRepository,
                query
            )
        }.flow
    }

    /**
     * Update query with category and keyword
     */
    private fun setQuery(
        keyword: String? = null,
    ) {
        Log.d(TAG, "Updating query!")
        viewModelScope.launch(
            defaultDispatcher
        ) {
            val category = _selectedCategory
            var query = defaultQuery
            val firestoreCategory = FirestoreIngredientCategory.getCategoryName(category)

            // set category name
            if (firestoreCategory != null) {
                query = query.whereEqualTo(
                    Ingredient.CATEGORY_KEY,
                    firestoreCategory
                )
            }

            keyword?.let { keyword ->
                query = query.whereArrayContains(
                    Ingredient.KEYWORDS_KEY,
                    keyword.lowercase()
                )
            }

            _ingredientPagingState.update {
                getIngredientsPagingFlow(query)
            }
        }
    }

    /**
     * Updates selected category and sets query
     * @param category
     */
    fun updateCategory(category: IngredientCategory) {
        _selectedCategory = category
        setQuery()
    }
}
