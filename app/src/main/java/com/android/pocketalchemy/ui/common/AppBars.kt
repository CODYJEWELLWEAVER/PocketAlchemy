package com.android.pocketalchemy.ui.common

import androidx.annotation.StringRes
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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
 * @param isBackButtonEnabled if back action should be shown
 * @param onClickBackButton callback for navigating back to
 * EditRecipeScreen
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PaTopAppBar(
    @StringRes titleId: Int,
    isBackButtonEnabled: Boolean = false,
    onClickBackButton: () -> Unit = {},
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
        actions = {
            if (isBackButtonEnabled) {
                TextButton(onClick = onClickBackButton) {
                    Text(
                        text = stringResource(
                            id = R.string.select_ingredient_back_button_label
                        ),
                        style = MaterialTheme.typography.titleLarge
                    )
                }
            }
        }
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
    isShoppingListsSelected: Boolean = false,
    isIngredientsSelected: Boolean = false
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
                    painter = painterResource(id = R.drawable.meal),
                    contentDescription = stringResource(R.string.meal_description)
                )
            },
            label = {
                Text(text = stringResource(id = R.string.recipe_list_nav_label))
            },
            colors = navigationBarItemColors
        )
        // SHOPPING LISTS NAV ITEM
        NavigationBarItem(
            selected = isShoppingListsSelected,
            enabled = !isShoppingListsSelected,
            onClick = { /*TODO*/ },
            icon = {
                Icon(
                    painter = painterResource(id = R.drawable.shopping_cart),
                    contentDescription = stringResource(R.string.shopping_cart_description),
                )
            },
            label = {
                Text(
                    text = stringResource(R.string.shopping_lists_nav_label)
                )
            },
            colors = navigationBarItemColors
        )
        // INGREDIENTS NAV ITEM
        NavigationBarItem(
            selected = isIngredientsSelected,
            enabled = !isIngredientsSelected,
            onClick = { /*TODO*/ },
            icon = {
                Icon(
                    painter = painterResource(id = R.drawable.bulleted_list),
                    contentDescription = stringResource(R.string.bulleted_list_description),
                )
            },
            label = {
                Text(
                    text = stringResource(R.string.ingredients_nav_label)
                )
            },
            colors = navigationBarItemColors
        )
    }
}