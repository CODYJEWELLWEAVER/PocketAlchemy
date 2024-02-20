package com.android.pocketalchemy.editrecipe.selectingredient

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.android.pocketalchemy.model.Ingredient

/**
 * Card used for displaying basic ingredient information in
 * ingredient selection pop up.
 * @param ingredient
 * @param onAddIngredient callback for adding selected ingredient
 * to current recipe
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun IngredientCard(
    ingredient: Ingredient,
    onAddIngredient: () -> Unit,
) {
    Card(
        onClick = {},
        modifier = Modifier
            .fillMaxWidth(1f)
            .padding(8.dp),
        shape = CardDefaults.elevatedShape,
        elevation = CardDefaults.cardElevation(4.dp),
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth(1f)
                .padding(8.dp)
                .heightIn(min = 50.dp),
        ) {
            Text(
                text = ingredient.fancyDescription,
                style = MaterialTheme.typography.headlineSmall,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .align(Alignment.Center)
                    .matchParentSize(),
            )
        }
    }
}