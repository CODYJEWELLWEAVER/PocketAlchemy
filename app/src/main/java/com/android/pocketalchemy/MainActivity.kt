package com.android.pocketalchemy

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.android.pocketalchemy.editrecipe.EditRecipeScreen
import com.android.pocketalchemy.editrecipe.EditRecipeViewModel
import com.android.pocketalchemy.firebase.AuthRepository
import com.android.pocketalchemy.recipelist.RecipeListScreen
import com.android.pocketalchemy.ui.theme.PocketAlchemyTheme
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.lifecycle.withCreationCallback
import javax.inject.Inject

private const val TAG = "MainActivity"

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var authRepository: AuthRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            PocketAlchemyTheme {
                val navController = rememberNavController()

                NavHost(navController, startDestination = "RecipeListScreen") {
                    composable("RecipeListScreen") {
                        RecipeListScreen(
                            onNavigateToNewRecipe = {
                                navController.navigate("CreateNewRecipe")
                            }
                        )
                    }
                    composable("CreateNewRecipe") {
                        val editRecipeViewModel by viewModels<EditRecipeViewModel>(
                            extrasProducer = {
                                defaultViewModelCreationExtras
                                    .withCreationCallback<EditRecipeViewModel.EditRecipeViewModelFactory> { factory ->
                                        factory.create(null)
                                    }
                            }
                        )

                        EditRecipeScreen(
                            navController,
                            editRecipeViewModel
                        )
                    }
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()
        // Check if an anonymous user is already signed-in
        // before signing in
        // TODO: REFACTOR FOR EMAIL:PASSWORD AUTH
        if (!authRepository.isUserSignedIn()) {
            Log.d(TAG, "Logging in...")
            authRepository.signInAnonymousUser()
        }
    }
}