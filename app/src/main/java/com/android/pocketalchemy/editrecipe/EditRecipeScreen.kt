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
import androidx.compose.runtime.collectAsState
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

@Composable
fun EditRecipeScreen(
    navController: NavController,
    editRecipeViewModel: EditRecipeViewModel
) {
    val appBarTitle = if (editRecipeViewModel.recipeId == null) {
        R.string.create_new_recipe_title
    } else { R.string.app_name }

    val recipeState: State<Recipe> = editRecipeViewModel.getRecipe().collectAsState()
    val recipe = recipeState.value

    Scaffold(
        contentColor = MaterialTheme.colorScheme.onBackground,
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            PaTopAppBar(titleId = appBarTitle)
        },
        bottomBar = {
            PaNavBar()
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
                            editRecipeViewModel.updateRecipe { oldRecipe ->
                                oldRecipe.copy(title = it)
                            }
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
                            editRecipeViewModel.updateRecipe { oldRecipe ->
                                oldRecipe.copy(subtitle = it)
                            }
                        },
                        label = {
                            Text(
                                text = stringResource(id = R.string.recipe_subtitle_label),
                            )
                        },
                    )
                }

                ///////////////
                // Recipe Icon
                ///////////////
                Column(
                    modifier = Modifier.height(TITLE_ROW_HEIGHT.dp)
                ) {
                    IconButton(
                        modifier = Modifier.fillMaxSize(1f),
                        onClick = { /*TODO*/ }
                    ) {
                        Icon(
                            painter = recipe.getIcon(),
                            contentDescription = recipe.getIconDesc()
                        )
                    }
                }
            }

            Row( // Description

            ) {
                // TODO:
            }

            Row( // Ingredients

            ) { /* TODO: */ }

            Row( // Recipe Instructions

            ) { /* TODO: */ }

            Row( // Cancel and Save buttons
                modifier = Modifier.padding(4.dp)
            ) {
                // Save button
                Box(
                    modifier = Modifier.padding(horizontal = 4.dp)
                ) {
                    Button(
                        onClick = {
                            editRecipeViewModel.saveRecipe()
                        },
                        modifier = Modifier.fillMaxWidth(.5f),
                        elevation = ButtonDefaults.buttonElevation(8.dp)
                    ) {
                        Text(text = stringResource(id = R.string.save_recipe_button_label))
                    }
                }

                // Cancel "go back" button
                Box(
                    modifier = Modifier.padding(horizontal = 4.dp)
                ) {
                    Button(
                        onClick = { navController.popBackStack() },
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
