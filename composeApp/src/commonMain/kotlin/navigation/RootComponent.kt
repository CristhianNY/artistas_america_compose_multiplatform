package navigation

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.pop
import com.arkivanov.decompose.router.stack.pushNew

class RootComponent(componentContext: ComponentContext) : ComponentContext by componentContext {
    private val navigation = StackNavigation<Configuration>()

    val childStack = childStack(
        source = navigation,
        serializer = Configuration.serializer(),
        initialConfiguration = Configuration.HomeScreen,
        handleBackButton = true,
        childFactory = ::createChild
    )

    private fun createChild(
        config: Configuration,
        context: ComponentContext
    ): Child {
        return when (config) {
            Configuration.HomeScreen -> Child.HomeScreen(
                HomeComponent(
                    context,
                    onNavigationToDashBoard = {
                        navigation.pushNew(Configuration.DashboardScreen)
                    })
            )

            is Configuration.DashboardScreen -> Child.DashBoardScreen(
                DashboardComponent(
                    context,
                    onBack = {
                        navigation.pop()
                    })
            )
        }
    }
}