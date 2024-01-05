package com.android.pocketalchemy.model

import androidx.annotation.DrawableRes
import androidx.annotation.Keep
import androidx.annotation.StringRes
import com.android.pocketalchemy.R
import com.google.firebase.firestore.DocumentId
import com.google.firebase.firestore.IgnoreExtraProperties

/**
 * Model for recipes
 * @property recipeId ID used to uniquely identify recipe document
 * @property userId ID of user who created this document
 * @property title String representing tiltle
 */
@IgnoreExtraProperties
@Keep
data class Recipe(
    @DocumentId val id: String? = null,
    val userId: String? = null,
    val title: String? = null,
    val subtitle: String? = null,
    val kcalPerServing: Int? = null,
    val description: String? = null,
    val date: String? = null,
    @DrawableRes val iconRes: Int? = null,
    @StringRes val iconDescRes: Int?  = null
) {
    companion object {
        // Field names to use when querying firestore
        const val USER_ID_FIELD = "userId"
    }
}

/**
 * Retrieves painter for recipe icon.
 */
fun Recipe.getIconRes(): Int {
    return this.iconRes ?: R.drawable.default_recipe_icon
}

/**
 * Retrieves string resource for recipe icon description.
 */
fun Recipe.getIconDescRes(): Int {
    return this.iconDescRes ?: R.string.default_recipe_icon_description
}