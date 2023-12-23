package com.android.pocketalchemy.firebase

import android.util.Log
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@Module
@InstallIn(ActivityComponent::class)
object AuthProvider {
    private const val EMULATOR_HOST = "10.0.2.2"
    private const val AUTH_EMULATOR_PORT = 9099
    private const val TAG = "AuthProvider"

    @Provides
    fun provideFirebaseAuth(): FirebaseAuth {
        val firebaseAuth = Firebase.auth

        if (/*Config.DEBUG*/false) {
            // Use emulator for debug
            Log.d(TAG, "Connecting to emulator...")
            firebaseAuth.useEmulator(EMULATOR_HOST, AUTH_EMULATOR_PORT)
        }

        return firebaseAuth
    }
}