package com.android.pocketalchemy.editrecipe

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.android.pocketalchemy.R
import com.android.pocketalchemy.model.Recipe
import com.android.pocketalchemy.model.getIcon
import com.android.pocketalchemy.model.getIconDesc
import com.android.pocketalchemy.ui.common.PaNavBar
import com.android.pocketalchemy.ui.common.PaTopAppBar

private const val TAG = "EditRecipeScreen"

// Title and Icon Row Height
private const val TITLE_ROW_HEIGHT = 150
private const val DESC_ROW_HEIGHT = 150

/**
 * Screen for creating and editing recipes.
 * @param navController NavController for current NavHost
 * @param editRecipeViewModel EditRecipeViewModel - should be initialized with setRecipeId()
 */
@Composable
fun EditRecipeScreen(
    navController: NavController,
    editRecipeViewModel: EditRecipeViewModel
) {
    val appBarTitle = R.string.edit_recipe_title
    val recipeState: State<Recipe> = editRecipeViewModel.recipeState
    val recipe = recipeState.value

    Scaffold(
        contentColor = MaterialTheme.colorScheme.onBackground,
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            PaTopAppBar(titleId = appBarTitle)
        },
        bottomBar = {
            PaNavBar(navController)
        }
    ) { scaffoldPadding ->
        Column(
            modifier = Modifier.padding(scaffoldPadding)
        ) {
            Row( // Title, subtitle, icon
                modifier = Modifier.padding(8.dp)
            ) {
                ////////////////////////////
                // Recipe Title and subtitle
                ////////////////////////////
                Column(
                    modifier = Modifier
                        .fillMaxWidth(.75f)
                        .height(TITLE_ROW_HEIGHT.dp)
                ) {
                    // Title
                    OutlinedTextField(
                        value = recipe.title ?: "",
                        onValueChange = {
                            val title = if (it == "") {
                                null
                            } else {
                                it
                            }
                            editRecipeViewModel.updateRecipeState(
                                recipe.copy(title = title)
                            )
                        },
                        label = {
                            Text(
                                text = stringResource(id = R.string.recipe_title_label),
                                style = MaterialTheme.typography.headlineLarge
                            )
                        },
                        textStyle = MaterialTheme.typography.headlineMedium,
                        maxLines = 1,
                    )
                    // Subtitle
                    OutlinedTextField(
                        value = recipe.subtitle ?: "",
                        onValueChange = {
                            val subtitle = if (it == "") {
                                null
                            } else {
                                it
                            }
                            editRecipeViewModel.updateRecipeState(
                                recipe.copy(subtitle = subtitle)
                            )
                        },
                        label = {
                            Text(
                                text = stringResource(id = R.string.recipe_subtitle_label),
                            )
                        },
                        maxLines = 1,
                    )
                }

                //////////////
                // Recipe Icon
                //////////////
                Column(
                    modifier = Modifier.height(TITLE_ROW_HEIGHT.dp)
                ) {
                    IconButton(
                        modifier = Modifier.fillMaxSize(1f),
                        onClick = { /*TODO: ICON SELECTOR*/ }
                    ) {
                        Icon(
                            painter = recipe.getIcon(),
                            contentDescription = recipe.getIconDesc()
                        )
                    }
                }
            }

            //////////////
            // Description
            //////////////
            Row(
                modifier = Modifier
                    .height(DESC_ROW_HEIGHT.dp)
                    .fillMaxWidth(1f)
                    .padding(horizontal = 8.dp)
            ) {
                OutlinedTextField(
                    value = recipe.description ?: "",
                    onValueChange = {
                        val description = if (it == "") {
                            null
                        } else {
                            it
                        }
                        editRecipeViewModel.updateRecipeState(
                            recipe.copy(description = description)
                        )
                    },
                    label = {
                        Text(
                            text = stringResource(id = R.string.recipe_description_label),
                            style = MaterialTheme.typography.bodyLarge
                        )
                    },
                    textStyle = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.fillMaxSize(1f)
                )
            }

            Row( // Ingredients

            ) { /* TODO: */ }

            Row( // Recipe Instructions

            ) { /* TODO: */ }

            //////////////////////////
            // Cancel and Save buttons
            //////////////////////////
            Row(
                modifier = Modifier.padding(4.dp)
            ) {
                // Save button
                Box(
                    modifier = Modifier.padding(horizontal = 4.dp, vertical = 8.dp)
                ) {
                    Button(
                        onClick = {
                            editRecipeViewModel.saveRecipe()
                            navController.popBackStack()
                        },
                        modifier = Modifier.fillMaxWidth(.5f),
                        elevation = ButtonDefaults.buttonElevation(8.dp)
                    ) {
                        Text(text = stringResource(id = R.string.save_recipe_button_label))
                    }
                }

                // Cancel "go back" button
                Box(
                    modifier = Modifier.padding(horizontal = 4.dp, vertical = 8.dp)
                ) {
                    Button(
                        onClick = {
                            editRecipeViewModel.clearRecipeId()
                            navController.popBackStack()
                        },
                        modifier = Modifier.fillMaxWidth(1f),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.secondaryContainer,
                            contentColor = MaterialTheme.colorScheme.onSecondaryContainer
                        ),
                        elevation = ButtonDefaults.buttonElevation(8.dp)
                    ) {
                        Text(text = stringResource(id = R.string.go_back_button_label))
                    }
                }
            }
        }
    }
}
