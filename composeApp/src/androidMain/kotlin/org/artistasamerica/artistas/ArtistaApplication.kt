package org.artistasamerica.artistas

import android.app.Application
import di.sharedModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import support.AndroidSettingsManager

class ArtistaApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@ArtistaApplication)
            androidLogger()
            modules(sharedModule)

        }
        AndroidSettingsManager.init(this)

    }
}