package di

import org.koin.dsl.module
import support.ImagePicker

val iosModule = module {
    factory { ImagePicker() }
}