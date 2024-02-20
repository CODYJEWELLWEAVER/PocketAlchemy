package com.android.pocketalchemy.editrecipe.selectingredient

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.android.pocketalchemy.model.ALL_CATEGORIES_NAME
import com.android.pocketalchemy.model.Ingredient
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

    private val _selectedCategory: MutableStateFlow<String?>
        = MutableStateFlow(null)

    val selectedCategory: StateFlow<String?>
        get() = _selectedCategory.asStateFlow()

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
            val categoryName = selectedCategory.value
            var query = defaultQuery

            // set category name
            when (categoryName) {
                ALL_CATEGORIES_NAME,
                null -> {} // no specific category selected
                else -> {
                    query = query.whereEqualTo(
                        "category",
                        categoryName
                    )
                }
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
     * @param categoryName
     */
    fun updateCategory(categoryName: String?) {
        _selectedCategory.update {
            categoryName
        }
        setQuery()
    }
}
