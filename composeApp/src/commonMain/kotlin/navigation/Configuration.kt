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
}
