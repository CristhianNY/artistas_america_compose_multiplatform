package navigation

import kotlinx.serialization.Serializable

@Serializable
sealed class Configuration {
    @Serializable
    object HomeScreen : Configuration()

    @Serializable
    object DashboardScreen : Configuration()
}
