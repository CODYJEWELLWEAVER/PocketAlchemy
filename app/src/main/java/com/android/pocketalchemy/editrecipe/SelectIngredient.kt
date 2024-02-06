package com.android.pocketalchemy.editrecipe

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.Popup
import com.android.pocketalchemy.R
import com.android.pocketalchemy.ui.common.PaTopAppBar

/**
 * Popup to allow users to select ingredient to add to
 * recipe.
 * @param onClickBackButton callback for closing pop-up screen
 */
@Composable
fun SelectIngredient(
    onClickBackButton: () -> Unit,
) {
    Popup {
        Scaffold(
            topBar = {
                PaTopAppBar(
                    titleId = R.string.select_ingredient_title,
                    isBackButtonEnabled = true,
                    onClickBackButton = onClickBackButton
                )
            },
        ) { scaffoldPadding ->
            Surface(
                modifier = Modifier
                    .fillMaxSize(1f)
                    .padding(scaffoldPadding)
            ) {
                LazyColumn(userScrollEnabled = false) {
                    // TODO: Display paged ingredients to select from
                }
            }
        }
    }
}