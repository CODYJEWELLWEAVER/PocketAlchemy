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
    @DocumentId val id: String = "",
    val description: String = "",
    val keywords: List<String> = listOf(),
    val category: String = "",
    val fdcId: String = "",
    val calories: Map<String, Any> = mapOf(),
    val protein: Map<String, Any> = mapOf(),
    val fat: Map<String, Any> = mapOf(),
    val carbs: Map<String, Any> = mapOf(),
    val sodium: Map<String, Any> = mapOf(),
    val fiber: Map<String, Any> = mapOf(),
    val sugars: Map<String, Any> = mapOf(),
    val measures: Map<String, Any> = mapOf()
) {
    companion object {
        const val UNIT_KEY = "unit"
        const val VALUE_KEY = "value"
        const val DESCRIPTION_KEY = "description"
    }
}