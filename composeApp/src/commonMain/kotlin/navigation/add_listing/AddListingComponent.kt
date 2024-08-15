package navigation.add_listing

import com.arkivanov.decompose.ComponentContext

class AddListingComponent(
    component: ComponentContext,
    private val onBack: () -> Unit,
    private val onNavigationToServiceNameScreen: () -> Unit,
) : ComponentContext by component {

    fun onEvent(event: AddListingEvent) {
        when (event) {
            AddListingEvent.GoBack -> onBack()
            AddListingEvent.GoToServiceNameScreen -> onNavigationToServiceNameScreen()
        }
    }
}