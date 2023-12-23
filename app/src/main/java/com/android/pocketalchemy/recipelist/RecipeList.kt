package com.android.pocketalchemy.recipelist

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
@Preview
fun RecipeList(
    recipeListViewModel: RecipeListViewModel = viewModel()
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp),
        contentPadding = PaddingValues(8.dp)
    ) {
        item {
            Box(
                modifier = Modifier.padding(vertical = 8.dp)
            ) {

            }
        }
    }
}