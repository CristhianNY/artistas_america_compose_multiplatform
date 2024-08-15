package navigation.add_listing

import com.arkivanov.decompose.ComponentContext

class AddFinalDetailsComponent(
    component: ComponentContext,
    private val onBack: () -> Unit,
    private val onNavigationToRequestReviewsScreen: () -> Unit,
) : ComponentContext by component {

    fun onEvent(event: AddFinalDetailsEvent) {
        when (event) {
            AddFinalDetailsEvent.GoBack -> onBack()
            AddFinalDetailsEvent.GoToRequestReviewsScreen -> onNavigationToRequestReviewsScreen()
        }
    }
}