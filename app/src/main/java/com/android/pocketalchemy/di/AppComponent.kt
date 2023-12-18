package com.android.pocketalchemy.di

import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [Subcomponents::class])
interface AppComponent