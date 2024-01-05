package com.android.pocketalchemy

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.android.pocketalchemy.editrecipe.EditRecipeScreen
import com.android.pocketalchemy.editrecipe.EditRecipeViewModel
import com.android.pocketalchemy.login.ErrOnLoginScreen
import com.android.pocketalchemy.login.LoginScreen
import com.android.pocketalchemy.recipelist.RecipeListScreen
import com.android.pocketalchemy.repository.AuthRepository

/**
 * Launches navigation host.
 */
@Composable
fun PaNavHost(
    authRepository: AuthRepository
) {
    val navController = rememberNavController()

    NavHost(navController, startDestination = "loginScreen") {
        composable("loginScreen") {
            LoginScreen {
                authRepository.signInAnonymousUser(
                    onSuccess = {
                        navController.navigateAndPopAll("recipeListScreen")
                    },
                    onFailure = {
                        navController.navigateAndPopAll("errOnLoginScreen")
                    }
                )
            }
        }
        composable("errOnLoginScreen") {
            ErrOnLoginScreen {
                navController.navigateAndPopAll("loginScreen")
            }
        }
        composable("recipeListScreen") {
            RecipeListScreen(
                navController,
                onNavigateToEditRecipe = { recipeId ->
                    if (recipeId == null) {
                        navController.navigate("editRecipe/")
                    } else {
                        navController.navigate("editRecipe/?recipeId=$recipeId")
                    }
                }
            )
        }
        composable(
            "editRecipe/?recipeId={recipeId}",
            arguments = listOf(navArgument("recipeId") { nullable = true; NavType.StringType })
        ) { backStackEntry ->
            val editRecipeViewModel = hiltViewModel<EditRecipeViewModel>()
            val recipeIdArg = remember { backStackEntry.arguments?.getString("recipeId") }

            LaunchedEffect(recipeIdArg) {
                editRecipeViewModel.setRecipeId(recipeIdArg)
            }

            EditRecipeScreen(
                navController,
                editRecipeViewModel,
            )
        }
    }
}

/**
 * Extension function to navigate to dest and clear back stack
 */
fun NavController.navigateAndPopAll(route: String) {
    this.navigate(route) {
        popUpTo(0)
    }
}