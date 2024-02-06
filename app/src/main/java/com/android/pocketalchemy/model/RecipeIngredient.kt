package com.android.pocketalchemy.model

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
 */
@Keep
@IgnoreExtraProperties
data class RecipeIngredient(
    @DocumentId val id: String? = null,
    val recipeId: String = "",
    val ingredientId: String = "",
    val description: String = "",
    val gramWeight: Float = 0f
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