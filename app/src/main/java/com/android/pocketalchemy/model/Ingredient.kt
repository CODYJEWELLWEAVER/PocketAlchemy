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
    val fdcId: Int = 0,
    val calories: Map<String, Any> = emptyMap(),
    val protein: Map<String, Any> = emptyMap(),
    val fat: Map<String, Any> = emptyMap(),
    val carbs: Map<String, Any> = emptyMap(),
    val sodium: Map<String, Any> = emptyMap(),
    val fiber: Map<String, Any> = emptyMap(),
    val sugars: Map<String, Any> = emptyMap(),
    val measures: List<Map<String, Any>> = emptyList(),
) {
    companion object {
        const val DESCRIPTION_KEY = "description"
        const val CATEGORY_KEY = "category"
        const val KEYWORDS_KEY = "keywords"
        const val UNIT_KEY = "unit"
        const val VALUE_KEY = "value"
        const val MEASURE_G_WEIGHT_KEY = "gWeight"
    }

    /**
     * Will be removed in the future once I have
     * updated firestore ingredient descriptions
     * to all begin with a capital letter
     */
    val fancyDescription: String
        /* TODO: Update firestore ingredient collection descriptions
            to have capitalized first letter. */
        get() {
            val firstLetter = description[0]
            val descLength = description.length
            return firstLetter.uppercase() + description.slice(1..<descLength)
        }
}

