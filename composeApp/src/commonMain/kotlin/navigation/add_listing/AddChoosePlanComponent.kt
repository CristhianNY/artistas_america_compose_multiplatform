package navigation.add_listing

import com.arkivanov.decompose.ComponentContext

class AddChoosePlanComponent(
    component: ComponentContext,
    private val onBack: () -> Unit,
    private val onNavigationToDashBoardScreen: () -> Unit,
) : ComponentContext by component {

    fun onEvent(event: AddChoosePlanEvent) {
        when (event) {
            AddChoosePlanEvent.GoBack -> onBack()
            AddChoosePlanEvent.GoToDashboardScreen -> onNavigationToDashBoardScreen()
        }
    }
}