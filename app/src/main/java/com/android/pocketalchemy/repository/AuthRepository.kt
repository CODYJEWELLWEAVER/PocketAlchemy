package com.android.pocketalchemy.repository

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.components.ViewModelComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject

@Module
@InstallIn(ActivityComponent::class, ViewModelComponent::class)
class AuthRepository @Inject constructor(
    private val auth: FirebaseAuth
) {

    /**
     * Returns user id string
     */
    fun getUserIdString(): String? {
        return auth.currentUser?.uid
    }

    /**
     * Gets current user or null.
     */
    fun getUser(): FirebaseUser? {
        return auth.currentUser
    }

    /**
     * Perform anonymous sign-in request
     * @param onSuccess callback for navigating to default landing page (RecipeListScreen)
     * @param onFailure callback for navigating to login error screen
     * @param coroutineScope coroutine scope to run sign in request in
     */
    fun signInAnonymousUser(
        onSuccess: () -> Unit,
        onFailure: () -> Unit,
        coroutineScope: CoroutineScope,
    ) {
        coroutineScope.launch {
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
        }
    }

    companion object {
        const val TAG = "AuthRepository"
    }
}