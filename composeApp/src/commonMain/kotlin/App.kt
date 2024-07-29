import add_listing.LandingListScreen
import admin_dashboard.DashboardLayout
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import com.arkivanov.decompose.extensions.compose.stack.Children
import com.arkivanov.decompose.extensions.compose.stack.animation.slide
import com.arkivanov.decompose.extensions.compose.stack.animation.stackAnimation
import home.HomeScreen
import navigation.Child
import navigation.RootComponent
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App(rootComponent: RootComponent) {
    MaterialTheme {
        val childStack by rootComponent.childStack.subscribeAsState()
        Children(
            stack = childStack,
            animation = stackAnimation(slide())
        ) { child ->
            when (val instance = child.instance) {
                is Child.DashBoardScreen -> DashboardLayout(instance.component)
                is Child.HomeScreen -> HomeScreen(instance.component)
                is Child.LandingListScreen -> LandingListScreen(instance.component)
            }
        }
    }
}
