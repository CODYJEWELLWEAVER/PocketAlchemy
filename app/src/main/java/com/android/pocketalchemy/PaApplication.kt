package com.android.pocketalchemy

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

object Config {
    const val DEBUG = true
}

@HiltAndroidApp
class PaApplication: Application()