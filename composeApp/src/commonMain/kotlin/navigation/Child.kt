package navigation

import navigation.add_listing.AddAddressComponent
import navigation.add_listing.AddChoosePlanComponent
import navigation.add_listing.AddDescriptionServiceComponent
import navigation.add_listing.AddFinalDetailsComponent
import navigation.add_listing.AddImageComponent
import navigation.add_listing.AddRequestReviewComponent
import navigation.add_listing.AddServiceNameComponent
import navigation.dashboard.DashboardComponent
import navigation.home.HomeComponent
import navigation.lading.LandingComponent
import navigation.payment.AddPaymentComponent

sealed class Child {
    data class DashBoardScreen(val component: DashboardComponent) : Child()
    data class HomeScreen(val component: HomeComponent) : Child()
    data class LandingListScreen(val component: LandingComponent): Child()
    data class ServiceActorNameScreen(val component: AddServiceNameComponent): Child()
    data class AddressScreen(val component: AddAddressComponent): Child()
    data class AddImagesScreen(val component: AddImageComponent): Child()
    data class DescribeServiceScreen(val component: AddDescriptionServiceComponent): Child()
    data class FinalDetailsScreen(val component: AddFinalDetailsComponent): Child()
    data class PriceTableScreen(val component: AddChoosePlanComponent): Child()
    data class RequestReviewsScreen(val component: AddRequestReviewComponent): Child()
    data class ChoosePlanScreen(val component: AddChoosePlanComponent): Child()
    data class AddPaymentScreen(val component: AddPaymentComponent): Child()
}
