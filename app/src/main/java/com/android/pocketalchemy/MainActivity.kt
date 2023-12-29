package com.android.pocketalchemy

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.android.pocketalchemy.firebase.AuthRepository
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

        // Check if an anonymous user is already signed-in
        // before signing in
        // TODO: REFACTOR FOR EMAIL:PASSWORD AUTH
        if (!authRepository.isUserSignedIn()) {
            Log.d(TAG, "Logging in...")
            authRepository.signInAnonymousUser()
        }

        setContent {
            PocketAlchemyTheme {
                PaNavHost()
            }
        }
    }
}