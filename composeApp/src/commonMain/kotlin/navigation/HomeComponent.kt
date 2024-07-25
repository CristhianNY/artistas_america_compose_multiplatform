package navigation

import com.arkivanov.decompose.ComponentContext

class HomeComponent(
    component: ComponentContext,
    private val onNavigationToDashBoard: () -> Unit
) : ComponentContext by component {
    fun onEvent(event: HomeEvent) {
        when (event) {
            HomeEvent.GoToDashboard -> onNavigationToDashBoard()
        }
    }

}