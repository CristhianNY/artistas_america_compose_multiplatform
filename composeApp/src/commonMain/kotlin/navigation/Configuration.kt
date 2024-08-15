package navigation

import kotlinx.serialization.Serializable

@Serializable
sealed class Configuration {
    @Serializable
    data class HomeScreen(val uniqueId: Int) : Configuration()

    @Serializable
    data class DashboardScreen(val uniqueId: Int) : Configuration()

    @Serializable
    data class LandingListScreen(val uniqueId: Int) : Configuration()

    @Serializable
    data class AddListingScreen(val uniqueId: Int) : Configuration()

    @Serializable
    data class AddressScreen(val uniqueId: Int) : Configuration()

    @Serializable
    data class AddImagesScreen(val uniqueId: Int) : Configuration()

    @Serializable
    data class DescribeServiceScreen(val uniqueId: Int) : Configuration()

    @Serializable
    data class FinalDetailsScreen(val uniqueId: Int) : Configuration()

    @Serializable
    data class PriceTableScreen(val uniqueId: Int) : Configuration()

    @Serializable
    data class RequestReviewsScreen(val uniqueId: Int) : Configuration()

    @Serializable
    data class ServiceActorNameScreen(val uniqueId: Int) : Configuration()
}
