package navigation.add_listing

import com.arkivanov.decompose.ComponentContext

class AddServiceNameComponent(
    component: ComponentContext,
    private val onBack: () -> Unit,
    private val onNavigationToAddressScreen: () -> Unit,
) : ComponentContext by component {

    fun onEvent(event: AddServiceNameEvent) {
        when (event) {
            AddServiceNameEvent.GoBack -> onBack()
            AddServiceNameEvent.GoToAddressScreen -> onNavigationToAddressScreen()
        }
    }
}