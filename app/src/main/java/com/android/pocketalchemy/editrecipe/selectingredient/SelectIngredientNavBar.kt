package com.android.pocketalchemy.editrecipe.selectingredient

import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.android.pocketalchemy.R

/**
 * Simple nav bar that allows for easy back navigation
 * from within select ingredient pop up
 * @param onClickBackButton lambda for back navigation
 */
@Composable
fun SelectIngredientNavBar(
    onClickBackButton: () -> Unit,
) {
    NavigationBar(
        containerColor = MaterialTheme.colorScheme.primaryContainer,
        contentColor = MaterialTheme.colorScheme.onPrimaryContainer
    ) {
        NavigationBarItem(
            selected = false,
            onClick = onClickBackButton,
            icon = {
                Icon(
                    painter = painterResource(id = R.drawable.back_arrow),
                    contentDescription = stringResource(id = R.string.back_arrow_description),
                )
            },
            label = {
                Text(
                    text = stringResource(
                        id = R.string.select_ingredient_back_button_label
                    ),
                )
            },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = MaterialTheme.colorScheme.onPrimaryContainer,
                selectedTextColor = MaterialTheme.colorScheme.onPrimaryContainer,
                indicatorColor = MaterialTheme.colorScheme.primaryContainer,
            )
        )
    }
}