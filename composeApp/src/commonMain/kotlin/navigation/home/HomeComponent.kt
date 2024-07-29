package navigation.home

import com.arkivanov.decompose.ComponentContext

class HomeComponent(
    component: ComponentContext,
    private val onNavigationToDashBoard: () -> Unit,
    private val onNavigationToJoin: () -> Unit,

    ) : ComponentContext by component {
    fun onEvent(event: HomeEvent) {
        when (event) {
            HomeEvent.GoToDashboard -> onNavigationToDashBoard()
            HomeEvent.GoToJoin -> onNavigationToJoin()
        }
    }
}