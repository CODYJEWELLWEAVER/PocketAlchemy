package com.android.pocketalchemy

import com.android.pocketalchemy.model.Recipe
import com.android.pocketalchemy.model.getIconDescRes
import com.android.pocketalchemy.model.getIconRes
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class PaUnitTest {

    @Test
    fun testRecipeDefaultResources() {
        var recipe = Recipe()

        assertEquals(recipe.getIconRes(), R.drawable.default_recipe_icon)
        assertEquals(recipe.getIconDescRes(), R.string.default_recipe_icon_description)

        recipe = Recipe(iconRes = 0, iconDescRes = 0)
        assertEquals(recipe.getIconRes(), 0)
        assertEquals(recipe.getIconDescRes(), 0)
    }
}