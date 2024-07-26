package navigation

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.lifecycle.LifecycleOwner

expect class RootComponent(
    componentContext: ComponentContext,
    urlHandler: UrlHandler? = null
) : ComponentContext {
    val childStack: Value<ChildStack<Configuration, Child>>
}

