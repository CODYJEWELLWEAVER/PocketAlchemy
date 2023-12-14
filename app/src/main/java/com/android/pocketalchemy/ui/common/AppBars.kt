package com.android.pocketalchemy.ui.common

import androidx.compose.foundation.layout.Column
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.android.pocketalchemy.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PaTopAppBar() {
    TopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            navigationIconContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
            titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
        ),
        title = {
            Text(
                text = stringResource(id = R.string.app_name)
            )
        },
    )
}

@Composable
fun PaNavBar() {
    NavigationBar(
        containerColor = MaterialTheme.colorScheme.primaryContainer,
        contentColor = MaterialTheme.colorScheme.onPrimaryContainer,
    ) {
        val navigationBarItemColors = NavigationBarItemDefaults.colors(
            selectedIconColor = MaterialTheme.colorScheme.onPrimaryContainer,
            selectedTextColor = MaterialTheme.colorScheme.onPrimaryContainer,
            indicatorColor = MaterialTheme.colorScheme.primaryContainer,
        )
        // RECIPE LIST NAV ITEM
        NavigationBarItem(
            selected = true,
            onClick = { /*TODO*/ },
            icon = {
                Column {
                    Icon(
                        painter = painterResource(id = R.drawable.bulleted_list),
                        contentDescription = stringResource(R.string.bulleted_list_description),
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )
                    Text(
                        text = stringResource(R.string.recipe_list_nav_label)
                    )
                }
            },
            colors = navigationBarItemColors,
        )
        // INGREDIENT LISTS NAV ITEM
        NavigationBarItem(
            selected = true,
            onClick = { /*TODO*/ },
            icon = {
                Column {
                    Icon(
                        painter = painterResource(id = R.drawable.shopping_cart),
                        contentDescription = stringResource(R.string.shopping_cart_description),
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )
                    Text(
                        text = stringResource(R.string.ingredient_lists_nav_label)
                    )
                }
            },
            colors = navigationBarItemColors,
        )
        // NUTRITION NAV ITEM
        NavigationBarItem(
            selected = true,
            onClick = { /*TODO*/ },
            icon = {
                Column {
                    Icon(
                        painter = painterResource(id = R.drawable.balance),
                        contentDescription = stringResource(R.string.balance_description),
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )
                    Text(
                        text = stringResource(R.string.nutrition_nav_label)
                    )
                }
            },
            colors = navigationBarItemColors,
        )
    }
}