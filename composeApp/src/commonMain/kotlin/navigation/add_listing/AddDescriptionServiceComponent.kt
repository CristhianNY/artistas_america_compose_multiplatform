package navigation.add_listing

import com.arkivanov.decompose.ComponentContext

class AddDescriptionServiceComponent(
    component: ComponentContext,
    private val onBack: () -> Unit,
    private val onNavigationToFinalDetailsScreen: () -> Unit,
) : ComponentContext by component {

    fun onEvent(event: AddDescriptionServiceEvent) {
        when (event) {
            AddDescriptionServiceEvent.GoBack -> onBack()
            AddDescriptionServiceEvent.GoToFinalDetailsScreen -> onNavigationToFinalDetailsScreen()
        }
    }
}