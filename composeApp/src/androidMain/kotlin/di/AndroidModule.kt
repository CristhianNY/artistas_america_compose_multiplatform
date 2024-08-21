package di

import ActivitySingleton
import org.koin.dsl.module
import support.ImagePicker

val androidModule = module {
    single { ActivitySingleton.activity }
    single { ImagePicker() }
}