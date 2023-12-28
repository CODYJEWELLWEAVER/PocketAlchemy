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

    // Returns current firebase user object
    // Only safe to use after calling isUserSignedIn()
    fun getUser(): FirebaseUser {
        return auth.currentUser!!
    }

    // Check if a user is currently signed in
    fun isUserSignedIn(): Boolean {
        return auth.currentUser != null
    }

    // Perform anonymous sign-in request
    fun signInAnonymousUser() {
        auth.signInAnonymously()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d(TAG, "Anonymous sign in completed successfully.")
                } else {
                    Log.w(TAG, "Anonymous sign in failed...")
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