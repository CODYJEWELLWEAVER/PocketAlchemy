package com.android.pocketalchemy.model
import androidx.annotation.Keep
import com.google.firebase.firestore.DocumentId
import com.google.firebase.firestore.IgnoreExtraProperties

/**
 * Ingredient Model class
 */
@IgnoreExtraProperties
@Keep
data class Ingredient(
    @DocumentId val id: String? = null,
    val name: String? = null,
    val category: String? = null,
    val calories: MeasuredFloat? = null,
    val protein: MeasuredFloat? = null,
    val fat: MeasuredFloat? = null,
    val carbs: MeasuredFloat? = null,
    val sodium: MeasuredFloat? = null,
    val fiber: MeasuredFloat? = null,
    val sugars: MeasuredFloat? = null,
    val portions: List<MeasuredFloat>? = null,
    val fdcId: String? = null
)

/**
 * TODO: extract possible units from ingredients and make enum
 */
data class MeasuredFloat(val value: Float, val unit: String)