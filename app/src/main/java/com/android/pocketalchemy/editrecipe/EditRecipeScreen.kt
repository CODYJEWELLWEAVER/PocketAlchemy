package com.android.pocketalchemy.editrecipe

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import com.android.pocketalchemy.ui.common.PaNavBar
import com.android.pocketalchemy.ui.common.PaTopAppBar

private const val TAG = "EditRecipeScreen"

// Title and Icon Row Height
private const val MAX_DESCRIPTION_HEIGHT = 100

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
    val editRecipeUiState: State<EditRecipeUiState>
        = editRecipeViewModel.editRecipeUiState.collectAsState()

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
            val recipe = editRecipeUiState.value.recipe

            TitleField(
                recipe = recipe,
                onUpdate = {
                    editRecipeViewModel.updateUiState(
                        recipe = recipe.copy(title = it)
                    )
                }
            )

            DescriptionField(
                recipe = recipe,
                onUpdate = {
                    editRecipeViewModel.updateUiState(
                        recipe = recipe.copy(description = it)
                    )
                }
            )

            Row( // Ingredients

            ) { /* TODO: */
                val ingredients = editRecipeUiState.value.ingredients
                Log.d(TAG, ingredients.toString())
            }

            Row( // Recipe Instructions

            ) { /* TODO: */ }

            // Cancel and Save buttons
            Row(
                modifier = Modifier.padding(4.dp)
            ) {
                SaveButton(
                    onClick = {
                        editRecipeViewModel.saveRecipe()
                        navController.popBackStack()
                    }
                )

                BackButton(
                    onClick = {
                        editRecipeViewModel.clearRecipeId()
                        navController.popBackStack()
                    }
                )
            }
        }
    }
}

/**
 * Draws title field and requests updates from view model.
 */
@Composable
private fun TitleField(
    recipe: Recipe,
    onUpdate: (String) -> Unit
) {
    Row(
        modifier = Modifier.padding(8.dp)
    ) {
        OutlinedTextField(
            value = recipe.title,
            onValueChange = {
                onUpdate(it)
            },
            modifier = Modifier.fillMaxWidth(1f),
            label = {
                Text(
                    text = stringResource(id = R.string.recipe_title_label),
                    style = MaterialTheme.typography.headlineLarge
                )
            },
            textStyle = MaterialTheme.typography.headlineMedium,
            maxLines = 1,
        )
    }
}

/**
 * Draws description field and requests updates from view model.
 */
@Composable
private fun DescriptionField(
    recipe: Recipe,
    onUpdate: (String?) -> Unit
) {
    Row(
        modifier = Modifier
            .heightIn(max = MAX_DESCRIPTION_HEIGHT.dp)
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
                onUpdate(description)
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
}

/**
 * Save Recipe Button
 */
@Composable
private fun SaveButton(
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier.padding(horizontal = 4.dp, vertical = 8.dp)
    ) {
        Button(
            onClick = onClick,
            modifier = Modifier.fillMaxWidth(.5f),
            elevation = ButtonDefaults.buttonElevation(8.dp)
        ) {
            Text(text = stringResource(id = R.string.save_recipe_button_label))
        }
    }
}

/**
 * Back Button
 */
@Composable
private fun BackButton(
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier.padding(horizontal = 4.dp, vertical = 8.dp)
    ) {
        Button(
            onClick = onClick,
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