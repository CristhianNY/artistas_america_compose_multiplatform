package navigation.add_listing

import com.arkivanov.decompose.ComponentContext

class AddImageComponent(
    component: ComponentContext,
    private val onBack: () -> Unit,
    private val onNavigationToDescriptionScreen: () -> Unit,
) : ComponentContext by component {

    fun onEvent(event: AddImageEvent) {
        when (event) {
            AddImageEvent.GoBack -> onBack()
            AddImageEvent.GotoDescriptionScreen -> onNavigationToDescriptionScreen()
        }
    }
}