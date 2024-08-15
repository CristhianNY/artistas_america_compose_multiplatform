package navigation.add_listing

import com.arkivanov.decompose.ComponentContext

class AddAddressComponent(
    component: ComponentContext,
    private val onBack: () -> Unit,
    private val onNavigationToUploadImagesScreen: () -> Unit,
) : ComponentContext by component {

    fun onEvent(event: AddAddressEvent) {
        when (event) {
            AddAddressEvent.GoBack -> onBack()
            AddAddressEvent.GoToUploadImagesScreen -> onNavigationToUploadImagesScreen()
        }
    }
}