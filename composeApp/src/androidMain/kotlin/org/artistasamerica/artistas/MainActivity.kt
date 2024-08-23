package org.artistasamerica.artistas

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import App
import com.arkivanov.decompose.retainedComponent
import navigation.RootComponent
import navigation.UrlHandler
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import setAsSingleton
import support.ImagePicker

class MainComponent : KoinComponent {
    val urlHandler: UrlHandler by inject()
    val imagePicker: ImagePicker by inject()
}

class MainActivity : ComponentActivity() {

    private lateinit var pickImageLauncher: ActivityResultLauncher<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val mainComponent = MainComponent()

        // Inicializar el ActivityResultLauncher
        pickImageLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
            mainComponent.imagePicker.handleImageResult(this, uri)
        }

        // Inicializar ImagePicker con el ActivityResultLauncher
        mainComponent.imagePicker.initLauncher(pickImageLauncher)

        val root = retainedComponent {
            RootComponent(it, mainComponent.urlHandler)
        }

        ActivitySingleton.activity = this

        setContent {
            App(root)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if (ActivitySingleton.activity == this) {
            ActivitySingleton.activity = null
        }
    }
}
