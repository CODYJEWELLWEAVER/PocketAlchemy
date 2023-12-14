package com.android.pocketalchemy.ui.recipelist

import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.android.pocketalchemy.model.Recipe

@Composable
fun RecipeList() {
    val recipeList: MutableList<Recipe> = mutableListOf()
    for (i in 0..<100) {
        recipeList.add(Recipe("$i"))
    }

    LazyColumn(
        modifier = Modifier.fillMaxHeight(1f)
    ) {


        recipeList.forEach {
            item { Text(text = it.name) }
        }
    }
}