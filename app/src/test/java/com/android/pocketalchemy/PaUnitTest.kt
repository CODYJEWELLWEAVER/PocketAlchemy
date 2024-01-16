package com.android.pocketalchemy

import com.android.pocketalchemy.model.Recipe
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class PaUnitTest {

    @Test
    fun recipeNumIngredientsEmptyTest() {
        // default recipe with empty ingredients list
        val testRecipe = Recipe()
        val expected = "0"
        assertEquals(expected, testRecipe.numIngredients)
    }

    @Test
    fun recipeNumIngredientsTest() {
        val testRecipe = Recipe(ingredients = listOf("", "", "", "", "", ""))
        val expected = "6"
        assertEquals(expected, testRecipe.numIngredients)
    }

    @Test
    fun recipeEstTimeInstantTest() {
        val testRecipe = Recipe()
        val expected = "Instant!"
        assertEquals(expected, testRecipe.estimatedTime)
    }

    @Test
    fun recipeEstTimeTest() {
        var testRecipe = Recipe(time = 60)
        var expected = "1 Hour"
        assertEquals(expected, testRecipe.estimatedTime)

        testRecipe = Recipe(time = 90)
        expected = "1 Hour, 30 Minutes"
        assertEquals(expected, testRecipe.estimatedTime)

        testRecipe = Recipe(time = 120)
        expected = "2 Hours"
        assertEquals(expected, testRecipe.estimatedTime)

        testRecipe = Recipe(time = 1501)
        expected = "1 Day, 1 Hour, 1 Minute"
        assertEquals(expected, testRecipe.estimatedTime)

        testRecipe = Recipe(time = 2880)
        expected = "2 Days"
        assertEquals(expected, testRecipe.estimatedTime)
    }
}