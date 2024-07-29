package navigation

import navigation.dashboard.DashboardComponent
import navigation.home.HomeComponent
import navigation.lading.LandingComponent

sealed class Child {
    data class DashBoardScreen(val component: DashboardComponent) : Child()
    data class HomeScreen(val component: HomeComponent) : Child()
    data class LandingListScreen(val component: LandingComponent): Child()
}