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
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.android.pocketalchemy.R
import com.android.pocketalchemy.recipe.Recipe

// Max number of lines of text for recipe descriptions.
private const val MAX_DETAIL_LINES = 7
// Max number of lines of ingredients.
// One less that MAX_DETAIL_LINES to account for
// ingredient list label.
private const val MAX_INGREDIENT_LINES = MAX_DETAIL_LINES - 1

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecipeListCard(
    recipe: Recipe,
    onClick: () -> Unit,
) {
    val cardElevation = CardDefaults.cardElevation(
        4.dp,
    )

    Card(
        onClick = onClick,
        enabled = true,
        elevation = cardElevation
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(1f)
        ) {
            //////////////////
            // Icon and Title:
            //////////////////
            Row (
                modifier = Modifier.padding(8.dp)
            ) {
                //////////////////
                // Recipe icon
                //////////////////
                Icon(
                    painter = painterResource(recipe.icon),
                    contentDescription = stringResource(recipe.iconDesc),
                    modifier = Modifier.fillMaxWidth(.3f),
                )
                Column {
                    //////////////////
                    // Recipe title
                    //////////////////
                    Text(
                        text = recipe.title,
                        modifier = Modifier
                            .fillMaxWidth(1f)
                            .fillMaxHeight(.6f),
                        style = MaterialTheme.typography.headlineLarge,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                    )
                    //////////////////
                    // Recipe subtitle
                    //////////////////
                    if (recipe.subtitle != "") {
                        Text(
                            text = recipe.subtitle,
                            modifier = Modifier
                                .fillMaxWidth(1f)
                                .fillMaxHeight(.4f),
                            style = MaterialTheme.typography.titleMedium,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                        )
                    }
                    //////////////////
                    // kcal Per Serving
                    //////////////////
                    Text(
                        text = "${recipe.kcalPerServing} Calories",
                        fontWeight = FontWeight.Light,
                    )
                }
            }

            ////////////////////////////////////
            // Ingredient List and Details Row
            ////////////////////////////////////
            Row(
                modifier = Modifier.padding(4.dp)

            ) {
                //////////////////
                // Ingredient list
                //////////////////
                Column (
                    modifier = Modifier
                        .padding(8.dp)
                        .fillMaxWidth(.4f)
                ) {
                    Text(
                        text = stringResource(id = R.string.ingredient_list_label),
                        modifier = Modifier.align(Alignment.CenterHorizontally),
                        fontWeight = FontWeight.Bold,
                    )
                    for (i in 0..<MAX_INGREDIENT_LINES) {
                        if (i < recipe.ingredients.size) {
                            val ingredient = recipe.ingredients[i]
                            val ingredientText: String = if (i != MAX_INGREDIENT_LINES - 1) {
                                ingredient.name
                            } else {
                                "${ingredient.name}..."
                            }
                            Text(
                                text = ingredientText,
                                modifier = Modifier.align(Alignment.CenterHorizontally),
                            )
                        }
                    }
                }
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
                    //////////////////
                    // Recipe details
                    //////////////////
                    Column (
                        modifier = Modifier
                            .fillMaxWidth(1f)
                            .padding(horizontal = 16.dp, vertical = 8.dp)
                    ) {
                        Text(text = "\t${recipe.description}",
                            modifier = Modifier.align(Alignment.CenterHorizontally),
                            fontSize = 17.sp,
                            style = MaterialTheme.typography.titleMedium,
                            maxLines = MAX_DETAIL_LINES,
                            overflow = TextOverflow.Ellipsis,
                        )
                    }
                }

            }
        }
    }
}