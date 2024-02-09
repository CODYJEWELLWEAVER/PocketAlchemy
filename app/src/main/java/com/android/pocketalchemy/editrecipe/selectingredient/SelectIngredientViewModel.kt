package com.android.pocketalchemy.editrecipe.selectingredient

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
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

    fun setQuery(
        category: String? = null,
        keyword: String? = null,
    ) {
        viewModelScope.launch(
            defaultDispatcher
        ) {
            var query = defaultQuery

            if (category != null) {
                query = query.whereEqualTo(
                    "category",
                    category
                )
            }

            if (keyword != null) {
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
}
