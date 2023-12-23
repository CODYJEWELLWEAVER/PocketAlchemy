package com.android.pocketalchemy

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.android.pocketalchemy.recipelist.RecipeListScreen
import com.android.pocketalchemy.ui.theme.PocketAlchemyTheme
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

private const val TAG = "MainActivity"

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PocketAlchemyTheme {
                RecipeListScreen()
            }
        }
    }

    override fun onStart() {
        super.onStart()
        // Check if an anonymous user is already signed-in
        // before signing in
        // TODO: REFACTOR FOR EMAIL:PASSWORD AUTH
        if (auth.currentUser == null) {
            auth.signInAnonymously()
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Log.d(TAG, "Anonymous sign in completed successfully.")
                    } else {
                        Log.w(TAG, "Anonymous sign in failed...")
                    }
                }
        }
        else {
            val user = auth.currentUser
        }
    }

    override fun onStop() {
        super.onStop()
        auth.signOut()
    }
}