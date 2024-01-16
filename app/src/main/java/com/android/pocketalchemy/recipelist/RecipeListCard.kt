package com.android.pocketalchemy.recipelist

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.android.pocketalchemy.R
import com.android.pocketalchemy.model.Recipe

// Max number of lines of text for recipe descriptions.
private const val MAX_DESCRIPTION_LINES = 3
// Max number of lines of ingredients.
// One less that MAX_DETAIL_LINES to account for

/**
 * Displays a recipe's highlights and overview.
 * @param recipe recipe to display on card
 * @param onNavigateToEditRecipe callback for editing recipe navigation
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecipeListCard(
    recipe: Recipe,
    onNavigateToEditRecipe: (String?) -> Unit,
) {

    Card(
        onClick = {
            onNavigateToEditRecipe(recipe.id)
        },
        enabled = true,
        elevation = CardDefaults.cardElevation(
            4.dp,
        )
    ) {
        Column(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth(1f)
        ) {
            val coroutineScope = rememberCoroutineScope()

            // Title
            Row {
                ///////////////
                // Recipe title
                ///////////////
                Text(
                    text = recipe.title,
                    style = MaterialTheme.typography.headlineLarge,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
            }

            // kcal Per Serving
            Row {
                Text(
                    text = "${recipe.kcalPerServing} Calories/Serving",
                    fontWeight = FontWeight.Light,
                )
            }

            // Est. completion time
            Row {
                val estimatedTime = recipe.estimatedTime
                val estimatedTimeString = if (recipe.time == 0) {
                    stringResource(
                        id = R.string.estimated_recipe_time_label,
                        estimatedTime
                    )
                } else {
                    // removes label for "Instant!" recipes
                    estimatedTime
                }

                Text(
                    text = estimatedTimeString,
                    fontWeight = FontWeight.Light
                )
            }

            // number of ingredients
            Row {
                Text(
                    text = stringResource(
                        id = R.string.number_of_ingredients_label,
                        recipe.numIngredients
                    ),
                    fontWeight = FontWeight.Light
                )
            }

            // Description Row
            Row(
                modifier = Modifier.padding(horizontal = 4.dp, vertical = 8.dp)

            ) {
                recipe.description?.let{ recipeDescription ->
                    Box(
                        // This box is used to draw outline around recipe description.
                        modifier = Modifier
                            .border(
                                BorderStroke(1.dp, MaterialTheme.colorScheme.onBackground),
                                shape = RoundedCornerShape(16.dp)
                            )
                            .padding(8.dp)
                            .fillMaxHeight(1f)
                    ) {
                        Column (
                            modifier = Modifier
                                .fillMaxWidth(1f)
                                .padding(horizontal = 16.dp, vertical = 8.dp)
                        ) {
                            Text(text = "\t${recipeDescription}",
                                modifier = Modifier.align(Alignment.CenterHorizontally),
                                fontSize = 17.sp,
                                style = MaterialTheme.typography.titleMedium,
                                maxLines = MAX_DESCRIPTION_LINES,
                                overflow = TextOverflow.Ellipsis,
                            )
                        }
                    }
                }
            }
        }
    }
}