package com.android.pocketalchemy.model
import androidx.annotation.Keep
import com.google.firebase.firestore.DocumentId
import com.google.firebase.firestore.IgnoreExtraProperties

/**
 * Ingredient Model class
 * TODO: Reflect change of nutrition structures in firestore
 */
@IgnoreExtraProperties
@Keep
data class Ingredient(
    @DocumentId val id: String = "",
    val description: String = "",
    val keywords: List<String> = listOf(),
    val category: String = "",
    val fdcId: String = "",
    val calories: Float = 0f,
    val protein: Float = 0f,
    val fat: Float = 0f,
    val carbs: Float = 0f,
    val sodium: Float = 0f,
    val fiber: Float = 0f,
    val sugars: Float = 0f,
    val measures: Float = 0f,
) {
    companion object {
        const val DESCRIPTION_KEY = "description"
    }
}

