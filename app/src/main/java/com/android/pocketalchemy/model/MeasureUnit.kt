package com.android.pocketalchemy.model

import com.android.pocketalchemy.R

enum class MeasureUnit(val labelResId: Int) {
    Kilograms(R.string.kilogram_unit_label),
    Grams(R.string.gram_unit_label),
    Milligrams(R.string.milligram_unit_label),
}

object MeasureUnitDefaults {
    val defaultUnit = MeasureUnit.Grams
}