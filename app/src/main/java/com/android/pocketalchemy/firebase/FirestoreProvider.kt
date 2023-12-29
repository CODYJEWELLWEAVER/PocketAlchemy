package com.android.pocketalchemy.firebase

import android.util.Log
import com.android.pocketalchemy.BuildConfig
import com.google.firebase.Firebase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Hilt Module for Firestore
 */
@Module
@InstallIn(SingletonComponent::class)
object FirestoreProvider {
    private const val EMULATOR_HOST = "10.0.2.2"
    private const val FIRESTORE_EMULATOR_PORT = 8080
    private const val TAG = "FirestoreProvider"

    @Provides
    @Singleton
    fun provideFirestore(): FirebaseFirestore {
        val firebaseFirestore = Firebase.firestore

        if (BuildConfig.DEBUG) {
            Log.d(TAG, "Connecting to emulator...")
            firebaseFirestore.useEmulator(EMULATOR_HOST, FIRESTORE_EMULATOR_PORT)
        }

        return firebaseFirestore
    }
}