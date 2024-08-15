package navigation.add_listing

import com.arkivanov.decompose.ComponentContext

class AddRequestReviewComponent(
    component: ComponentContext,
    private val onBack: () -> Unit,
    private val onNavigationToAllDoneScreen: () -> Unit,
) : ComponentContext by component {

    fun onEvent(event: AddRequestReviewEvent) {
        when (event) {
            AddRequestReviewEvent.GoBack -> onBack()
            AddRequestReviewEvent.GoToAllDoneScreen -> onNavigationToAllDoneScreen()
        }
    }
}