package com.android.pocketalchemy.model

import androidx.annotation.DrawableRes
import androidx.annotation.Keep
import androidx.annotation.StringRes
import com.google.firebase.firestore.IgnoreExtraProperties

@IgnoreExtraProperties
@Keep
data class Recipe(
    val userId: String? = null,
    val title: String? = null,
    val subtitle: String? = null,
    val kcalPerServing: Int? = null,
    val description: String? = null,
    val date: String? = null,
    @DrawableRes val icon: Int? = null,
    @StringRes val iconDesc: Int?  = null
) {

    companion object {
        const val USER_ID_FIELD = "userId"
    }
}
