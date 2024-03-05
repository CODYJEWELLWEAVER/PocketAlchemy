package com.android.pocketalchemy.util


/**
 * operator function for addition of two Number
 * instances. Uses float addition under the hood.
 * @param other
 * @return The result of adding the receiver and
 * [other].
 *
 *
 * NOTE:
 * From my research it seems that Floats, i.e. (32-bit Floating-Point Numbers)
 * will perform more consistently across all devices. It seems that some
 * mobile devices can perform 32-bit operations at much higher rates
 * compared to 64-bit floating-point operations, especially lower end hardware.
 * #TODO: Research this claim more.
 */
operator fun Number.plus(other: Number): Number {
    return this.toFloat() + other.toFloat()
}
