package com.android.pocketalchemy.editrecipe.selectingredient

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.Dialog
import com.android.pocketalchemy.model.Ingredient


/**
 * Dialog to select the quantity of an ingredient to
 * add to recipe.
 * @param ingredient selected ingredient
 * @param onDismiss clears dialog
 * @param onAddIngredient callback to add ingredient with
 * selected quantity to recipe
 */
@Composable
fun SelectIngredientQuantityDialog(
    ingredient: Ingredient,
    onDismiss: () -> Unit,
    onAddIngredient: (Ingredient, Float) -> Unit,
) {
    Dialog(onDismissRequest = onDismiss) {
        Box(modifier = Modifier.fillMaxSize(.5f)) {
            Text(
                text = ingredient.fancyDescription,
                modifier = Modifier.align(Alignment.Center)
            )

            /* TODO: Add quantity selection UI */
        }
    }
}
