package navigation.payment

import com.arkivanov.decompose.ComponentContext

class AddPaymentComponent(
    component: ComponentContext,
    private val onBack: () -> Unit,
    private val onNavigationToDashboard: () -> Unit,
) : ComponentContext by component {

    fun onEvent(event: AddPaymentEvent) {
        when (event) {
            AddPaymentEvent.GoBack -> onBack()
            AddPaymentEvent.GoToDashboardScreen -> onNavigationToDashboard()
        }
    }
}