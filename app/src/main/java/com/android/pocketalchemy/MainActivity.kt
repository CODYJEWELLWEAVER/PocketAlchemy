package com.android.pocketalchemy

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.android.pocketalchemy.firebase.AuthRepository
import com.android.pocketalchemy.recipelist.RecipeListScreen
import com.android.pocketalchemy.ui.theme.PocketAlchemyTheme
import dagger.hilt.android.AndroidEntryPoint
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
                    composable("RecipeListScreen") { RecipeListScreen() }
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
            authRepository.signInAnonymousUser()
        }
    }
}