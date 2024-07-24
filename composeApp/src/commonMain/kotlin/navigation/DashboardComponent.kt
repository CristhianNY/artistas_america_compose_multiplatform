package navigation

import com.arkivanov.decompose.ComponentContext

class DashboardComponent(
    component: ComponentContext,
    private val onBack: () -> Unit
) : ComponentContext by component {

    fun goBack() {
        onBack.invoke()
    }

}