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
    val calories: MeasuredInt? = null,
    val protein: MeasuredInt? = null,
    val fat: MeasuredInt? = null,
    val carbs: MeasuredInt? = null,
    val sodium: MeasuredInt? = null,
    val fiber: MeasuredInt? = null,
    val sugars: MeasuredInt? = null,
    val portions: List<MeasuredInt>? = null,
    val fdcId: String? = null
)

/**
 * TODO: extract possible units from ingredients and make enum
 */
data class MeasuredInt(val value: Int, val unit: String)