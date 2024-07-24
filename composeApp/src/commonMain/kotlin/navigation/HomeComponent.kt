package navigation

import com.arkivanov.decompose.ComponentContext

class HomeComponent(
    component: ComponentContext,
    private val onBack: () -> Unit
) : ComponentContext by component {

    fun goBack() {
        onBack.invoke()
    }

}