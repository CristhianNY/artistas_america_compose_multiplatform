package di

import org.koin.dsl.module
import support.ImagePicker

val wasModule = module {
    single { ImagePicker() }
}