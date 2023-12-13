package com.android.pocketalchemy
import com.android.pocketalchemy.dagger.ApplicationGraph
import dagger.*
import android.app.Application
import com.android.pocketalchemy.dagger.DaggerApplicationGraph

class PocketAlchemyApplication: Application() {
    val applicationGraph = DaggerApplicationGraph.create()

}