package com.android.pocketalchemy.recipelist

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import com.android.pocketalchemy.R
import com.android.pocketalchemy.ui.common.PaNavBar
import com.android.pocketalchemy.ui.common.PaTopAppBar

/**
 * Creates scaffold environment for RecipeList
 * @param navController NavController for current NavHost
 * @param onNavigateToEditRecipe callback for navigation to edit/create recipe
 */
@Composable
fun RecipeListScreen(
    navController: NavController,
    onNavigateToEditRecipe: (String?) -> Unit,
) {
    Scaffold(
        contentColor = MaterialTheme.colorScheme.onBackground,
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            PaTopAppBar(titleId = R.string.app_name)
        },
        floatingActionButton = {
            NewRecipeFAB() { onNavigateToEditRecipe(null) }
        },
        bottomBar = {
            PaNavBar(navController, isRecipeListSelected = true)
        }
    ) { scaffoldPadding ->
        Column(
            modifier = Modifier.padding(scaffoldPadding)
        ) {
            RecipeList(onNavigateToEditRecipe)
        }
    }
}

/**
 * FAB to launch recipe creation.
 * @param onNavigateToEditRecipe
 * callback for navigating to new recipe creation screen
 */
@Composable
fun NewRecipeFAB(
    onNavigateToEditRecipe: () -> Unit
) {
    ExtendedFloatingActionButton(
        containerColor = MaterialTheme.colorScheme.primaryContainer,
        contentColor = MaterialTheme.colorScheme.onPrimaryContainer,
        text = {
            Text(
                stringResource(id = R.string.new_recipe_fab_label)
            )
        },
        icon = {
            Icon(
                painter = painterResource(id = R.drawable.create),
                contentDescription = stringResource(id = R.string.create_description),
            )
        },
        onClick = { onNavigateToEditRecipe() }
    )
}