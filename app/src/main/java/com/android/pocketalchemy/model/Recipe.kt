package com.android.pocketalchemy.model

import androidx.annotation.DrawableRes
import androidx.annotation.Keep
import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.android.pocketalchemy.R
import com.google.firebase.firestore.DocumentId
import com.google.firebase.firestore.IgnoreExtraProperties

@IgnoreExtraProperties
@Keep
data class Recipe(
    @DocumentId val recipeId: String? = null,
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
        const val USER_ID_FIELD = "userId"
    }
}

/**
 * Retrieves painter for recipe icon.
 */
@Composable
fun Recipe.getIcon(): Painter {
    return if (this.iconRes == null) {
        painterResource(id = R.drawable.default_recipe_icon)
    } else {
        painterResource(id = this.iconRes)
    }
}

/**
 * Retrieves string resource for recipe icon description.
 */
@Composable
fun Recipe.getIconDesc(): String {
    return if (this.iconDescRes == null) {
        stringResource(id = R.string.default_recipe_icon_description)
    } else {
        stringResource(id = this.iconDescRes)
    }
}