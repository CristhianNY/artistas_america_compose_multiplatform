package navigation.lading

import com.arkivanov.decompose.ComponentContext

class LandingComponent(
    component: ComponentContext,
    private val onBack: () -> Unit,
    private val goToServiceActorNameScreen: () -> Unit
) : ComponentContext by component {

    fun onEvent(event: LandingEvent) {
        when (event) {
            LandingEvent.GoToDashboard -> onBack()
            is LandingEvent.GoToServiceActorNameScreen -> goToServiceActorNameScreen() // Llamar a la función
            is LandingEvent.GoBack -> onBack()
        }
    }
}
