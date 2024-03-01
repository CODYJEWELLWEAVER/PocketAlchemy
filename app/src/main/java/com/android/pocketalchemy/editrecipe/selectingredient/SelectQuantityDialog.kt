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
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.android.pocketalchemy.R
import com.android.pocketalchemy.model.Ingredient
import com.android.pocketalchemy.model.MeasureUnitDefaults


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
        var surfaceSize by remember { mutableStateOf(IntSize.Zero) }

        Surface(
            modifier = Modifier
                .fillMaxWidth(0.75f)
                .fillMaxHeight(0.4f)
                .onGloballyPositioned {
                    surfaceSize = it.size
                },
            color = MaterialTheme.colorScheme.primaryContainer,
        ) {

            Box(
                modifier = Modifier
                    .fillMaxSize(1f)
                    .padding(8.dp)
            ) {
                var quantity by remember { mutableFloatStateOf(0.0f) }
                var unitMenuExpanded by remember { mutableStateOf(false) }
                var unit by remember { mutableStateOf(MeasureUnitDefaults.defaultUnit) }
                var buttonSize by remember { mutableStateOf(IntSize.Zero) }

                Text(
                    text = ingredient.fancyDescription,
                    modifier = Modifier.align(Alignment.TopCenter),
                    textAlign = TextAlign.Center
                )

                Column(
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .fillMaxWidth(1f)
                        .padding(8.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    QuantityField(
                        quantity = quantity,
                        onValueChange = {
                            val newQuantity = it.toFloatOrNull()
                            if (newQuantity != null) {
                                quantity = newQuantity
                            }
                        },
                    )

                    Button(
                        onClick = { unitMenuExpanded = !unitMenuExpanded },
                        modifier = Modifier
                            .fillMaxWidth(0.8f)
                            .padding(8.dp)
                            .onGloballyPositioned {
                                buttonSize = it.size
                            }
                    ) {
                        Text(
                            text = stringResource(id = unit.labelResId),
                            style = MaterialTheme.typography.bodyLarge,
                            maxLines = 1
                        )

                        //val unitMenuOffset = (100)
                        //Log.d("Test", "$surfaceSize")

                        DropdownMenu(
                            expanded = unitMenuExpanded,
                            onDismissRequest = { unitMenuExpanded = false },
                            //offset = DpOffset(x = unitMenuOffset.dp, y=0.dp)
                        ) {
                            DropdownMenuItem(
                                text = { /*TODO*/ },
                                onClick = { /*TODO*/ },
                            )
                        }
                    }
                }
            }
        }

    }
}

@Composable
private fun QuantityField(
    quantity: Float,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    TextField(
        value = quantity.toString(),
        onValueChange = { onValueChange(it) },
        modifier = modifier.fillMaxWidth(0.5f),
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
    ingredient: Ingredient = Ingredient(description = "Preview Ingredient Description"),
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