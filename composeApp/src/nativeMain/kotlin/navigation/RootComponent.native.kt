package navigation

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.pop
import com.arkivanov.decompose.router.stack.pushNew
import com.arkivanov.decompose.value.Value
import navigation.dashboard.DashboardComponent
import navigation.home.HomeComponent
import navigation.lading.LandingComponent

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
actual class RootComponent actual constructor(
    componentContext: ComponentContext,
    private val urlHandler: UrlHandler?
) : ComponentContext by componentContext {

    private val navigation = StackNavigation<Configuration>()

    actual val childStack: Value<ChildStack<Configuration, Child>> = childStack(
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
                    onNavigationToJoin = {
                        navigation.pushNew(Configuration.LandingListScreen)
                    },
                    onNavigationToDashBoard = {
                        navigation.pushNew(Configuration.DashboardScreen)
                    })
            )

            is Configuration.DashboardScreen -> Child.DashBoardScreen(DashboardComponent(context) {
                navigation.pop()
            })

            is Configuration.LandingListScreen -> Child.LandingListScreen(LandingComponent(context) {
                navigation.pushNew(Configuration.LandingListScreen)
            })
        }
    }
}
