package navigation

import kotlinx.serialization.Serializable

@Serializable
sealed class Configuration {
    @Serializable
    data object HomeScreen : Configuration()

    @Serializable
    data object DashboardScreen : Configuration()

    @Serializable
    data object LandingListScreen : Configuration()
}
