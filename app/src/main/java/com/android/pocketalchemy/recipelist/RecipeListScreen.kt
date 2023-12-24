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
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.android.pocketalchemy.R
import com.android.pocketalchemy.ui.common.PaNavBar
import com.android.pocketalchemy.ui.common.PaTopAppBar

private const val MIN_HEIGHT_FOR_TOP_BAR: Int = 500
@Preview
@Composable
fun RecipeListScreen(
    recipeListViewModel: RecipeListViewModel = hiltViewModel<RecipeListViewModel>()
) {
    val configuration = LocalConfiguration.current

    Scaffold(
        contentColor = MaterialTheme.colorScheme.onBackground,
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            if (configuration.screenHeightDp >= MIN_HEIGHT_FOR_TOP_BAR) {
                PaTopAppBar()
            }
        },
        floatingActionButton = {
            NewRecipeFAB(
                onClick = {
                    // TODO:
                }
            )
        },
        bottomBar = {
            PaNavBar()
        }
    ) { scaffoldPadding ->
        Column(
            modifier = Modifier.padding(scaffoldPadding)
        ) {
            RecipeList(recipeListViewModel)
        }
    }
}

@Composable
private fun NewRecipeFAB(
    onClick: () -> Unit
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
        onClick = { onClick() }
    )
}