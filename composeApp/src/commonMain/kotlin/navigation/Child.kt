package navigation

sealed class Child {
    data class DashBoardScreen(val component: DashboardComponent) : Child()
    data class HomeScreen(val component: HomeComponent) : Child()
}