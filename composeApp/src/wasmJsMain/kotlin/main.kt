import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.window.ComposeViewport
import com.arkivanov.decompose.DefaultComponentContext
import com.arkivanov.essenty.lifecycle.LifecycleRegistry
import di.sharedModule
import di.wasModule
import kotlinx.browser.document
import kotlinx.browser.window
import navigation.RootComponent
import navigation.UrlHandler
import org.koin.core.context.startKoin
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

@OptIn(ExperimentalComposeUiApi::class)
fun main() {
    startKoin {
        modules(sharedModule, wasModule)
    }

    val lifecycle = LifecycleRegistry()
    val rootComponentContext = DefaultComponentContext(lifecycle)

    // Define a KoinComponent wrapper to use Koin injection
    class MainComponent : KoinComponent {
        val urlHandler: UrlHandler by inject()
    }

    val mainComponent = MainComponent()

    ComposeViewport(document.body!!) {
        val root = RootComponent(rootComponentContext, mainComponent.urlHandler)
        App(root)
    }

    lifecycle.onCreate()
    window.addEventListener("beforeunload") {
        lifecycle.onDestroy()
    }
}
