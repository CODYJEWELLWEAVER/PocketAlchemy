package com.android.pocketalchemy.editrecipe.selectingredient

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.android.pocketalchemy.R
import com.android.pocketalchemy.model.Ingredient
import com.android.pocketalchemy.util.MeasureUnit
import com.android.pocketalchemy.util.MeasureUnitDefaults


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

        Surface(
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .fillMaxHeight(0.5f),
            color = MaterialTheme.colorScheme.primaryContainer,
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize(1f)
                    .padding(8.dp)
            ) {
                var quantity by remember { mutableStateOf("0") }
                var unit by remember { mutableStateOf(MeasureUnitDefaults.defaultUnit) }

                // Ingredient Overview UI
                Column(
                    modifier = Modifier
                        .align(Alignment.TopCenter)
                        .fillMaxWidth(1f)
                        .padding(8.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Text(
                        text = ingredient.fancyDescription,
                        textAlign = TextAlign.Center
                    )

                    Divider(
                        modifier = Modifier.padding(vertical = 8.dp),
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )

                    NutritionInfo(
                        quantity = quantity,
                        ingredient = ingredient,
                    )
                }

                // Quantity Selection UI
                Column(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .fillMaxWidth(1f)
                        .padding(8.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    QuantityField(quantity = quantity) {
                        val newQuantity = it.toFloatOrNull()
                        if (
                            (newQuantity != null || it.isEmpty())
                            && newQuantity != 0f
                        ) {
                            quantity = it
                        }
                    }

                    UnitSelectionButton(currentUnit = unit) {
                        unit = it
                    }
                }
            }
        }

    }
}

@Composable
private fun NutritionInfo(
    quantity: String,
    ingredient: Ingredient,
) {
    val floatQuantity = quantity.toFloatOrNull()
    if (floatQuantity == null || floatQuantity == 0f) {
        Text(
            text = stringResource(id = R.string.enter_to_see_nutrition_label),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.bodyMedium,
        )
    } else {
        val calories = ingredient.calories[Ingredient.VALUE_KEY] as Number?
        val protein = ingredient.protein[Ingredient.VALUE_KEY] as Number?
        val fat = ingredient.fat[Ingredient.VALUE_KEY] as Number?
        val carbs = ingredient.carbs[Ingredient.VALUE_KEY] as Number?
        val sodium = ingredient.sodium[Ingredient.VALUE_KEY] as Number?
        val sugars = ingredient.sugars[Ingredient.VALUE_KEY] as Number?

        // TODO: Build annotated strings for displaying nutritional information.
    }
}

@Composable
private fun UnitSelectionButton(
    currentUnit: MeasureUnit,
    onSelectUnit: (MeasureUnit) -> Unit,
) {
    var unitMenuExpanded by remember { mutableStateOf(false) }
    Button(
        onClick = { unitMenuExpanded = !unitMenuExpanded },
        modifier = Modifier
            .fillMaxWidth(0.8f)
            .padding(8.dp)
    ) {
        Text(
            text = stringResource(id = currentUnit.labelResId),
            style = MaterialTheme.typography.bodyLarge,
            maxLines = 1,
            textAlign = TextAlign.Center
        )

        DropdownMenu(
            expanded = unitMenuExpanded,
            onDismissRequest = { unitMenuExpanded = false },
        ) {
            for (measureUnit in MeasureUnit.entries) {
                val measureUnitLabel = stringResource(id = measureUnit.labelResId)
                DropdownMenuItem(
                    text = {
                        Text(
                            text = measureUnitLabel,
                            style = MaterialTheme.typography.labelMedium,
                            textAlign = TextAlign.Center,
                        )
                    },
                    onClick = {
                        unitMenuExpanded = false
                        onSelectUnit(measureUnit)
                    },
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
            }
        }
    }
}

/**
 * Accepts user input for quantity information
 * from user. On selection the user is presented with
 * a decimal keyboard.
 * @param quantity current quantity
 * @param onValueChange updates quantity if the input from the user
 * can be converted to a valid floating point number.
 */
@Composable
private fun QuantityField(
    quantity: String,
    onValueChange: (String) -> Unit,
) {
    TextField(
        value = quantity,
        onValueChange = { onValueChange(it) },
        modifier = Modifier.fillMaxWidth(0.5f),
        keyboardOptions = KeyboardOptions.Default.copy(
            keyboardType = KeyboardType.Decimal
        ),
        singleLine = true,
        label = {
            Text(
                stringResource(id = R.string.quantity_field_label),
                style = MaterialTheme.typography.bodyMedium,
                maxLines = 1,
                textAlign = TextAlign.Center
            )
        },
        textStyle = MaterialTheme.typography.bodyLarge
    )
}

@Preview
@Composable
private fun PreviewDialog(
    ingredient: Ingredient = Ingredient(description = "Preview Ingredient Description", calories=mapOf("value" to 1)),
    onDismiss: () -> Unit = {},
    onAddIngredient: (Ingredient, Float) -> Unit = { _: Ingredient, _: Float -> },
) {
    Surface(
        modifier = Modifier
            .height(750.dp)
            .width(300.dp)
    ) {
        SelectIngredientQuantityDialog(
            ingredient = ingredient,
            onDismiss = onDismiss,
            onAddIngredient = onAddIngredient
        )
    }
}