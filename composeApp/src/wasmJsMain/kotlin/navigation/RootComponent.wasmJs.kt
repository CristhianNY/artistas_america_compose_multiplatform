package navigation

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.pop
import com.arkivanov.decompose.router.stack.push
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.lifecycle.Lifecycle
import com.arkivanov.essenty.lifecycle.LifecycleOwner
import com.arkivanov.essenty.lifecycle.doOnDestroy
import navigation.dashboard.DashboardComponent
import navigation.home.HomeComponent
import navigation.lading.LandingComponent

private var uniqueIdCounter = 0

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
actual class RootComponent actual constructor(
    componentContext: ComponentContext,
    private val urlHandler: UrlHandler?
) : ComponentContext by componentContext, LifecycleOwner {

    private val navigation = StackNavigation<Configuration>()
    override val lifecycle: Lifecycle = componentContext.lifecycle

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

        urlHandler?.onPathChanged = { path ->
            handleUrlChange(path)
        }

        lifecycle.doOnDestroy {
            urlHandler?.onPathChanged = null
        }
    }

    private fun createChild(
        config: Configuration,
        context: ComponentContext
    ): Child {
        return when (config) {
            is Configuration.HomeScreen -> Child.HomeScreen(
                HomeComponent(
                    context,
                    onNavigationToJoin = {
                        navigateTo(Configuration.LandingListScreen(uniqueId = generateUniqueId()))
                    },
                    onNavigationToDashBoard = {
                        navigateTo(Configuration.DashboardScreen(uniqueId = generateUniqueId()))
                    })
            )

            is Configuration.DashboardScreen -> Child.DashBoardScreen(DashboardComponent(context) {
                navigation.pop()
            })

            is Configuration.LandingListScreen -> Child.LandingListScreen(LandingComponent(context) {
                navigateTo(Configuration.DashboardScreen(uniqueId = generateUniqueId()))
            })
        }
    }

    private fun updateUrl(configuration: Configuration) {
        val path = when (configuration) {
            is Configuration.HomeScreen -> "home"
            is Configuration.DashboardScreen -> "dashboard"
            is Configuration.LandingListScreen -> "unirte"
        }
        urlHandler?.pushUrl(path)
    }

    private fun getInitialConfiguration(): Configuration {
        return when (urlHandler?.getPath()) {
            "dashboard" -> Configuration.DashboardScreen(uniqueId = generateUniqueId())
            "unirte" -> Configuration.LandingListScreen(uniqueId = generateUniqueId())
            else -> Configuration.HomeScreen(uniqueId = generateUniqueId())
        }
    }

    private fun handleUrlChange(path: String) {
        val newConfiguration = when (path) {
            "dashboard" -> Configuration.DashboardScreen(uniqueId = generateUniqueId())
            "unirte" -> Configuration.LandingListScreen(uniqueId = generateUniqueId())
            else -> Configuration.HomeScreen(uniqueId = generateUniqueId())
        }
        val currentConfiguration = childStack.value.active.configuration
        if (currentConfiguration != newConfiguration) {
            navigation.push(newConfiguration)
        }
    }

    private fun navigateTo(newConfiguration: Configuration) {
        val currentConfiguration = childStack.value.active.configuration
        if (currentConfiguration::class != newConfiguration::class) {
            navigation.push(newConfiguration)
        } else if (currentConfiguration != newConfiguration) {
            navigation.pop()
            navigation.push(newConfiguration)
        }
    }

    private fun generateUniqueId(): Int {
        return uniqueIdCounter++
    }
}