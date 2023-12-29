package com.android.pocketalchemy.ui.common

import androidx.annotation.StringRes
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.android.pocketalchemy.R

/**
 * Top app bar for PocketAlchemy
 * @param titleId string resource id for title
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PaTopAppBar(
    @StringRes titleId: Int
) {
    TopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            navigationIconContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
            titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
        ),
        title = {
            Text(
                text = stringResource(id = titleId)
            )
        },
    )
}

/**
 * Navigation bar
 * @param navController NavController for current nav host
 * @param isRecipeListSelected true if on recipe list screen.
 * @param isIngredientsSelected true if on ingredients screen.
 * @param isNutritionSelected true if on nutrition screen.
 */
@Composable
fun PaNavBar(
    navController: NavController,
    isRecipeListSelected: Boolean = false,
    isIngredientsSelected: Boolean = false,
    isNutritionSelected: Boolean = false,
) {
    NavigationBar(
        containerColor = MaterialTheme.colorScheme.primaryContainer,
        contentColor = MaterialTheme.colorScheme.onPrimaryContainer,
        tonalElevation = 8.dp,
    ) {
        // Modifies default nav bar item colors.
        val navigationBarItemColors = NavigationBarItemDefaults.colors(
            selectedIconColor = MaterialTheme.colorScheme.onPrimaryContainer,
            selectedTextColor = MaterialTheme.colorScheme.onPrimaryContainer,
            indicatorColor = MaterialTheme.colorScheme.primaryContainer,
        )
        
        // RECIPE LIST NAV ITEM
        NavigationBarItem(
            selected = isRecipeListSelected,
            enabled = !isRecipeListSelected,
            onClick = {
                navController.navigate("recipeListScreen")
            },
            icon = {
                Icon(
                    painter = painterResource(id = R.drawable.bulleted_list),
                    contentDescription = stringResource(R.string.bulleted_list_description)
                )
            },
            label = {
                Text(text = stringResource(id = R.string.recipe_list_nav_label))
            },
            colors = navigationBarItemColors
        )
        // INGREDIENT LISTS NAV ITEM
        NavigationBarItem(
            selected = isIngredientsSelected,
            enabled = !isIngredientsSelected,
            onClick = { /*TODO*/ },
            icon = {
                Icon(
                    painter = painterResource(id = R.drawable.shopping_cart),
                    contentDescription = stringResource(R.string.shopping_cart_description),
                )
            },
            label = {
                Text(
                    text = stringResource(R.string.ingredient_lists_nav_label)
                )
            },
            colors = navigationBarItemColors
        )
        // NUTRITION NAV ITEM
        NavigationBarItem(
            selected = isNutritionSelected,
            enabled = !isNutritionSelected,
            onClick = { /*TODO*/ },
            icon = {
                Icon(
                    painter = painterResource(id = R.drawable.balance),
                    contentDescription = stringResource(R.string.balance_description),
                )
            },
            label = {
                Text(
                    text = stringResource(R.string.nutrition_nav_label)
                )
            },
            colors = navigationBarItemColors
        )
    }
}