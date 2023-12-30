package com.android.pocketalchemy.firebase

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.components.ViewModelComponent
import javax.inject.Inject

@Module
@InstallIn(ActivityComponent::class, ViewModelComponent::class)
class AuthRepository @Inject constructor(
    private val auth: FirebaseAuth
) {

    /**
     * Returns a non-null user id string
     */
    fun getUserIdString(): String? {
        return auth.currentUser?.uid
    }

    /**
    * Returns current firebase user object.
    * NOTE: Not safe in LoginScreen!!!
    */
    fun getUser(): FirebaseUser {
        return auth.currentUser!!
    }

    /**
     * Perform anonymous sign-in request
     * @param onSuccess callback for navigating to default landing page (RecipeListScreen)
     * @param onFailure callback for navigating to login error screen TODO: make error login screen
     */
    fun signInAnonymousUser(
        onSuccess: () -> Unit,
        onFailure: () -> Unit,
    ) {
        auth.signInAnonymously()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d(TAG, "Anonymous sign in completed successfully.")
                    onSuccess()
                } else {
                    Log.w(TAG, "Anonymous sign in failed...")
                    onFailure()
                }
            }
            .addOnFailureListener {
                // DEBUG USE
                throw it
            }
    }

    companion object {
        const val TAG = "AuthRepository"
    }
}