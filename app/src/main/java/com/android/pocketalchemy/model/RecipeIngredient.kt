package com.android.pocketalchemy.model

import com.android.pocketalchemy.util.MeasureUnit
import com.android.pocketalchemy.util.MeasureUnitDefaults
import com.google.errorprone.annotations.Keep
import com.google.firebase.firestore.DocumentId
import com.google.firebase.firestore.IgnoreExtraProperties

/**
 * A lightweight representation of a
 * an ingredient in a recipe.
 * @param recipeId ID of recipe ingredient belongs to
 * @param ingredientId
 * @param description
 * @param gramWeight
 * TODO: Add unit and unit quantity
 */
@Keep
@IgnoreExtraProperties
data class RecipeIngredient(
    @DocumentId val id: String? = null,
    val recipeId: String = "",
    val ingredientId: String = "",
    val description: String = "",
    val unit: MeasureUnit = MeasureUnitDefaults.defaultUnit,
    val value: Number = 0.0,
    ) {
    /**
     * Shortened recipe description for recipe view
     */
    val name: String
        get() {
            val splitDescription = description.split(", ")
            val nameWords = mutableListOf<String>()
            for (i in 0..<4) {
                val descWords = splitDescription.size
                if (i < descWords) {
                    nameWords.add(splitDescription[i])
                } else {
                    break
                }
            }
            if (splitDescription.size > 4) {
                nameWords.add("...")
            }
            return nameWords.joinToString(" ")
        }

    companion object {
        /** Field Names */
        const val RECIPE_ID_KEY = "recipeId"
        const val INGREDIENT_ID_KEY = "ingredientId"
    }
}