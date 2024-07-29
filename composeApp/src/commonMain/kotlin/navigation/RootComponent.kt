package navigation

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.value.Value

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
expect class RootComponent(
    componentContext: ComponentContext,
    urlHandler: UrlHandler? = null
) : ComponentContext {
    val childStack: Value<ChildStack<Configuration, Child>>
}

