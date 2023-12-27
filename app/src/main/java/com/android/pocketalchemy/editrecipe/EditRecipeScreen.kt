package com.android.pocketalchemy.editrecipe

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.android.pocketalchemy.R
import com.android.pocketalchemy.model.Recipe
import com.android.pocketalchemy.ui.common.PaNavBar
import com.android.pocketalchemy.ui.common.PaTopAppBar

private const val TAG = "EditRecipeScreen"

// Title and Icon Row Height
private const val TITLE_ROW_HEIGHT = 150

@Composable
fun EditRecipeScreen(
    recipeDocumentId: String? = null,
    editRecipeViewModel: EditRecipeViewModel = hiltViewModel()
) {
    val appBarTitle = if (recipeDocumentId == null) {
        R.string.create_new_recipe_title
    } else { R.string.app_name }

    var recipe = remember { mutableStateOf(editRecipeViewModel.getRecipeObject(recipeDocumentId)) }

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
                        .fillMaxWidth(.6f)
                        .height(TITLE_ROW_HEIGHT.dp)
                ) {
                    // Title
                    OutlinedTextField(
                        readOnly = false,
                        value = recipe.value.title ?: "",
                        onValueChange = {
                            recipe.value =  recipe.value.copy(title = it)
                        },
                        label = {
                            Text(
                                text = stringResource(id = R.string.recipe_title_label),
                                style = MaterialTheme.typography.headlineLarge
                            )
                        },
                        textStyle = MaterialTheme.typography.headlineLarge
                    )
                    // Subtitle
                    OutlinedTextField(
                        value = "",
                        onValueChange = {},
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
                            painter = getRecipeIcon(recipe.value),
                            contentDescription = getRecipeIconDesc(recipe.value)
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

            // Save recipe button
            Button(onClick = {  }) {
                Text(text = stringResource(id = R.string.save_recipe_button_label))
            }
        }
    }
}

@Composable
fun getRecipeIcon(recipe: Recipe): Painter {
    return if (recipe.icon == null) {
        painterResource(id = R.drawable.default_recipe_icon)
    } else {
        painterResource(id = recipe.icon)
    }
}

@Composable
fun getRecipeIconDesc(recipe: Recipe): String {
    return if (recipe.iconDesc == null) {
        stringResource(id = R.string.default_recipe_icon_description)
    } else {
        stringResource(id = recipe.iconDesc)
    }
}