import add_listing.presentation.LandingListScreen
import admin_dashboard.DashboardLayout
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import com.arkivanov.decompose.extensions.compose.stack.Children
import com.arkivanov.decompose.extensions.compose.stack.animation.slide
import com.arkivanov.decompose.extensions.compose.stack.animation.stackAnimation
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import home.HomeScreen
import navigation.Child
import navigation.RootComponent
import org.jetbrains.compose.ui.tooling.preview.Preview
import support.ArtistasTheme

@Composable
@Preview
fun App(rootComponent: RootComponent) {
    ArtistasTheme(primaryColor = Color(0xFF007BFF)) {
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
