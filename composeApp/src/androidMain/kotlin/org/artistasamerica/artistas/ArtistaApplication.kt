package org.artistasamerica.artistas

import android.app.Application
import android.content.Context
import di.sharedModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import support.AndroidSettingsManager

class ArtistaApplication : Application() {

    init {
        instance = this
    }

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@ArtistaApplication)
            androidLogger()
            modules(sharedModule)

        }
        AndroidSettingsManager.init(this)

    }

    companion object {
        private var instance: ArtistaApplication? = null

        val context: Context
            get() = instance!!.applicationContext
    }
}