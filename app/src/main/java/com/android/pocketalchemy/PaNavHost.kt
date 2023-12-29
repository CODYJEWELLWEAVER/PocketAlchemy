package com.android.pocketalchemy

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.android.pocketalchemy.editrecipe.EditRecipeScreen
import com.android.pocketalchemy.editrecipe.EditRecipeViewModel
import com.android.pocketalchemy.recipelist.RecipeListScreen

/**
 * Launches navigation host.
 */
@Composable
fun PaNavHost() {
    val navController = rememberNavController()

    NavHost(navController, startDestination = "recipeListScreen") {
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
            val recipeIdArg = backStackEntry.arguments?.getString("recipeId")
            editRecipeViewModel.setRecipeId(recipeIdArg)

            EditRecipeScreen(
                navController,
                editRecipeViewModel
            )
        }
    }
}