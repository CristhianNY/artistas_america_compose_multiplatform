package org.artistasamerica.artistas

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import App
import com.arkivanov.decompose.retainedComponent
import navigation.RootComponent
import navigation.UrlHandler
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class MainComponent : KoinComponent {
    val urlHandler: UrlHandler by inject()
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val mainComponent = MainComponent()
        val root = retainedComponent {
            RootComponent(it, mainComponent.urlHandler)
        }
        setContent {
            App(root)
        }
    }
}