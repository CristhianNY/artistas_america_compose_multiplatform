// shared/src/iosMain/kotlin/navigation/RootComponentIos.kt
package navigation

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.pop
import com.arkivanov.decompose.router.stack.pushNew
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.lifecycle.doOnDestroy

actual class RootComponent actual constructor(
    componentContext: ComponentContext,
    private val urlHandler: UrlHandler?
) : ComponentContext by componentContext {
    private val navigation = StackNavigation<Configuration>()

    actual val childStack: Value<ChildStack<Configuration, Child>> = childStack(
        source = navigation,
        serializer = Configuration.serializer(),
        initialConfiguration = getInitialConfiguration(),
        handleBackButton = true,
        childFactory = ::createChild
    )

    init {
        childStack.subscribe { stack ->
            updateUrl(stack.active.configuration)
        }

        componentContext.lifecycle.doOnDestroy {
            // Cleanup code if necessary
        }
    }

    private fun createChild(
        config: Configuration,
        context: ComponentContext
    ): Child {
        return when (config) {
            Configuration.HomeScreen -> Child.HomeScreen(HomeComponent(context) {
                navigation.pushNew(Configuration.DashboardScreen)
            })
            is Configuration.DashboardScreen -> Child.DashBoardScreen(DashboardComponent(context) {
                navigation.pop()
            })
        }
    }

    private fun updateUrl(configuration: Configuration) {
        val path = when (configuration) {
            Configuration.HomeScreen -> "home"
            Configuration.DashboardScreen -> "dashboard"
        }
        urlHandler?.pushUrl(path)
    }

    private fun getInitialConfiguration(): Configuration {
        return when (urlHandler?.getPath()) {
            "dashboard" -> Configuration.DashboardScreen
            else -> Configuration.HomeScreen
        }
    }
}
