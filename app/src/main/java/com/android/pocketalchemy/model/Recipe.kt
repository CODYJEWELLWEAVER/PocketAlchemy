package com.android.pocketalchemy.model

import androidx.annotation.Keep
import com.google.firebase.firestore.DocumentId
import com.google.firebase.firestore.IgnoreExtraProperties

/**
 * Model for recipes
 * @property id ID used to uniquely identify recipe document
 * @property userId ID of user who created this document
 * @property title String representing title
 */
@IgnoreExtraProperties
@Keep
data class Recipe(
    @DocumentId val id: String = "",
    val userId: String = "",
    val title: String = "",
    val kcalPerServing: Int = 0,
    val servingGramWeight: Int = 100, // default serving size of 100 g
    val description: String? = null,
    val date: String = "",
    val ingredients: List<String> = listOf(),
    val time: Int = 0, // # of minutes to complete recipe
) {

    /**
     * Returns a formatted string displaying estimated time to
     * complete recipe.
     */

    val estimatedTime: String
        get() {
            val days: Int = this.time / MINUTES_PER_DAY
            val hours: Int = (this.time - (MINUTES_PER_DAY * days)) / MINUTES_PER_HOUR
            val minutes: Int = this.time % MINUTES_PER_HOUR

            var estTimeParts = mutableListOf<String>()

            if (days >= 1) {
                estTimeParts += "$days "
                estTimeParts += if (days == 1) {
                    "Day"
                } else {
                    "Days"
                }
            }

            if (hours >= 1) {
                estTimeParts += "$hours "
                estTimeParts += if (hours == 1) {
                    "Hour"
                } else {
                    "Hours"
                }
            }

            if (minutes >= 1) {
                estTimeParts += "$minutes "
                estTimeParts += if (minutes == 1) {
                    "Minute"
                } else {
                    "Minutes"
                }
            }

            val estTimeString = estTimeParts.joinToString(separator = ", ")

            return if (estTimeString == "") {
                "Instant!"
            } else {
                estTimeString
            }
        }

    /**
     * Returns string representation of
     * number of ingredient in recipe.
     */
    val numIngredients: String
        get() = this.ingredients.size.toString()

    companion object {
        // Field names to use when querying firestore
        const val USER_ID_FIELD = "userId"
        const val MINUTES_PER_DAY: Int = 1440
        const val MINUTES_PER_HOUR: Int = 60
    }
}