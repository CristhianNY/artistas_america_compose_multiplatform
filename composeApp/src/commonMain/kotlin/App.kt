import add_listing.presentation.LandingListScreen
import add_listing.presentation.add_listing_steps.AddImagesScreen
import add_listing.presentation.add_listing_steps.AddressScreen
import add_listing.presentation.add_listing_steps.ChoosePlanScreen
import add_listing.presentation.add_listing_steps.DescribeServiceScreen
import add_listing.presentation.add_listing_steps.FinalDetailsScreen
import add_listing.presentation.add_listing_steps.RequestReviewsScreen
import add_listing.presentation.add_listing_steps.ServiceNameScreen
import payment.PaymentScreen
import admin_dashboard.DashboardLayout
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import com.arkivanov.decompose.extensions.compose.stack.Children
import com.arkivanov.decompose.extensions.compose.stack.animation.slide
import com.arkivanov.decompose.extensions.compose.stack.animation.stackAnimation
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import home.HomeScreen
import navigation.Child.*
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
                is DashBoardScreen -> DashboardLayout(instance.component)
                is HomeScreen -> HomeScreen(instance.component)
                is LandingListScreen -> LandingListScreen(instance.component)
                is ServiceActorNameScreen -> ServiceNameScreen(instance.component)
                is AddImagesScreen -> AddImagesScreen(instance.component)
                is AddressScreen -> AddressScreen(instance.component)
                is DescribeServiceScreen -> DescribeServiceScreen(instance.component)
                is FinalDetailsScreen -> FinalDetailsScreen(instance.component)
                is PriceTableScreen -> PriceTableScreen(instance.component)
                is RequestReviewsScreen -> RequestReviewsScreen(instance.component)
                is ChoosePlanScreen -> ChoosePlanScreen(instance.component)
                is AddPaymentScreen -> PaymentScreen(instance.component)
            }
        }
    }
}
