package com.android.pocketalchemy

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.android.pocketalchemy.editrecipe.EditRecipeScreen
import com.android.pocketalchemy.editrecipe.EditRecipeViewModel
import com.android.pocketalchemy.login.ErrorOnLoginScreen
import com.android.pocketalchemy.login.LoginScreen
import com.android.pocketalchemy.recipelist.RecipeListScreen
import com.android.pocketalchemy.repository.AuthRepository
import kotlinx.coroutines.CoroutineScope

/**
 * Launches navigation host.
 * @param authRepository
 */
@Composable
fun PaNavHost(
    authRepository: AuthRepository
) {
    val navController = rememberNavController()
    val coroutineScope = rememberCoroutineScope()

    NavHost(navController, startDestination = "loginScreen") {
        composable("loginScreen") {
            LoginScreenRoute(
                authRepository = authRepository,
                navController = navController,
                coroutineScope = coroutineScope
            )
        }
        composable("errOnLoginScreen") {
            ErrorOnLoginRoute(navController = navController)
        }
        composable("recipeListScreen") {
            RecipeListRoute(navController = navController)
        }
        composable(
            "editRecipe/?recipeId={recipeId}",
            arguments = listOf(navArgument("recipeId") { nullable = true; NavType.StringType })
        ) { backStackEntry ->
            EditRecipeRoute(
                navController = navController,
                backStackEntry = backStackEntry,
            )
        }
    }
}

/**
 * Routes to [LoginScreen]
 * @param authRepository
 * @param navController
 * @param coroutineScope CoroutineScope used for authentication.
 */
@Composable
fun LoginScreenRoute(
    authRepository: AuthRepository,
    navController: NavController,
    coroutineScope: CoroutineScope,
) {
    LoginScreen {
        authRepository.signInAnonymousUser(
            onSuccess = {
                navController.navigateAndPopAll("recipeListScreen")
            },
            onFailure = {
                navController.navigateAndPopAll("errOnLoginScreen")
            },
            coroutineScope = coroutineScope
        )
    }
}

/**
 * Routes to [ErrorOnLoginScreen]
 * @param navController
 */
@Composable
fun ErrorOnLoginRoute(
    navController: NavController,
) {
    ErrorOnLoginScreen {
        navController.navigateAndPopAll("loginScreen")
    }
}

/**
 * Routes to [RecipeListScreen] filled with current users
 * recipes.
 * @param navController
 */
@Composable
fun RecipeListRoute(
    navController: NavController,
) {
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

/**
 * Routes to [EditRecipeScreen] to either edit an
 * existing recipe or create a new recipe.
 * @param navController
 * @param backStackEntry Used to extract recipe ID from
 * navigation host - null if new recipe is being created
 */
@Composable
fun EditRecipeRoute(
    navController: NavController,
    backStackEntry: NavBackStackEntry,
) {
    val editRecipeViewModel = hiltViewModel<EditRecipeViewModel>()
    val recipeIdArg = remember { backStackEntry.arguments?.getString("recipeId") }

    LaunchedEffect(recipeIdArg) {
        editRecipeViewModel.setRecipe(recipeIdArg)
    }

    EditRecipeScreen(
        navController,
        editRecipeViewModel,
    )
}

/**
 * Extension function to navigate to destination and clear back stack
 * @param route String of route to navigate to within [PaNavHost].
 */
fun NavController.navigateAndPopAll(route: String) {
    this.navigate(route) {
        popUpTo(0)
    }
}