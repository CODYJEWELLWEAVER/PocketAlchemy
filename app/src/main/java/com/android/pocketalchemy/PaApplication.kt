package com.android.pocketalchemy

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

private const val TAG = "PaApplication"

@HiltAndroidApp
class PaApplication: Application()