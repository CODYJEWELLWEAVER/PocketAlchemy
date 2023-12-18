package com.android.pocketalchemy.recipe

import com.android.pocketalchemy.ingredient.Ingredient
import java.time.LocalDateTime

data class Recipe(
    val name: String,
    val ingredients: List<Ingredient>,
    val dateAdded: LocalDateTime,
    // TODO: ADD FIELDS
)
