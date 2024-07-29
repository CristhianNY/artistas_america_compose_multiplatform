package navigation.lading

import com.arkivanov.decompose.ComponentContext

class LandingComponent(
    component: ComponentContext,
    private val onBack: () -> Unit
) : ComponentContext by component {

    fun onEvent(event: LandingEvent) {
        when (event) {
            LandingEvent.GoToDashboard -> onBack()
        }
    }
}