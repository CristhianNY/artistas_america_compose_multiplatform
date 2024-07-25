// shared/src/commonMain/kotlin/navigation/RootComponent.kt
package navigation

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.value.Value

expect class RootComponent(
    componentContext: ComponentContext,
    urlHandler: UrlHandler? = null
) : ComponentContext {
    val childStack: Value<ChildStack<Configuration, Child>>
}
