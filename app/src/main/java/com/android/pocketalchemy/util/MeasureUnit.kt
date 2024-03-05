package com.android.pocketalchemy.util

import com.android.pocketalchemy.R


/**
 * Units of measurement for ingredients.
 */
enum class MeasureUnit(val labelResId: Int) {
    Kilograms(R.string.kilogram_unit_label),
    Grams(R.string.gram_unit_label),
    Milligrams(R.string.milligram_unit_label),
}

/**
 * Defaults for units of measures and their quantities.
 */
object MeasureUnitDefaults {
    val defaultUnit = MeasureUnit.Grams
}