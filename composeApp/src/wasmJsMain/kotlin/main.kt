import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.window.ComposeViewport
import com.arkivanov.decompose.DefaultComponentContext
import com.arkivanov.essenty.lifecycle.LifecycleRegistry
import di.sharedModule
import kotlinx.browser.document
import kotlinx.browser.window
import navigation.RootComponent
import org.koin.core.context.startKoin

@OptIn(ExperimentalComposeUiApi::class)
fun main() {
    startKoin {
        modules(sharedModule)
    }

    val lifecycle = LifecycleRegistry()
    val rootComponentContext = DefaultComponentContext(lifecycle)

    ComposeViewport(document.body!!) {
        val root = RootComponent(rootComponentContext)
        App(root)
    }

    lifecycle.onCreate()
    window.addEventListener("beforeunload") {
        lifecycle.onDestroy()
    }
}
