package com.android.pocketalchemy.recipe

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.android.pocketalchemy.R
import com.android.pocketalchemy.category.Category
import com.android.pocketalchemy.ingredient.Ingredient
import java.time.LocalDateTime

data class Recipe(
    val title: String,
    val subtitle: String = "",
    val ingredients: List<Ingredient>,
    val kcalPerServing: UInt,
    val date: LocalDateTime,
    val description: String,
    val categories: List<Category>,
    @DrawableRes val icon: Int = R.drawable.default_recipe_icon,
    @StringRes val iconDesc: Int = R.string.default_recipe_icon_description,
)
