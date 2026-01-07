package com.someguy590.workit

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.annotation.KoinApplication
import org.koin.ksp.generated.defaultModule
import org.koin.ksp.generated.startKoin

class MainApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        WorkItApp.startKoin {
            androidContext(this@MainApplication)
            defaultModule()
        }
    }
}

@KoinApplication
object WorkItApp
