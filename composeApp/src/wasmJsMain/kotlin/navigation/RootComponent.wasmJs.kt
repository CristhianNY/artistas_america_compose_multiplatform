package navigation

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.pop
import com.arkivanov.decompose.router.stack.push
import com.arkivanov.decompose.router.stack.pushNew
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.lifecycle.Lifecycle
import com.arkivanov.essenty.lifecycle.LifecycleOwner
import com.arkivanov.essenty.lifecycle.doOnDestroy
import navigation.add_listing.AddAddressComponent
import navigation.add_listing.AddChoosePlanComponent
import navigation.add_listing.AddDescriptionServiceComponent
import navigation.add_listing.AddFinalDetailsComponent
import navigation.add_listing.AddImageComponent
import navigation.add_listing.AddListingComponent
import navigation.add_listing.AddRequestReviewComponent
import navigation.add_listing.AddServiceNameComponent
import navigation.dashboard.DashboardComponent
import navigation.home.HomeComponent
import navigation.lading.LandingComponent
import navigation.payment.AddPaymentComponent

private var uniqueIdCounter = 0

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
actual class RootComponent actual constructor(
    componentContext: ComponentContext,
    private val urlHandler: UrlHandler?
) : ComponentContext by componentContext, LifecycleOwner {

    private val navigation = StackNavigation<Configuration>()
    override val lifecycle: Lifecycle = componentContext.lifecycle

    actual val childStack: Value<ChildStack<Configuration, Child>> = childStack(
        source = navigation,
        serializer = Configuration.serializer(),
        initialConfiguration = getInitialConfiguration(),
        handleBackButton = true,
        childFactory = ::createChild
    )

    init {
        childStack.subscribe { stack ->
            updateUrl(stack.active.configuration)
        }

        urlHandler?.onPathChanged = { path ->
            handleUrlChange(path)
        }

        lifecycle.doOnDestroy {
            urlHandler?.onPathChanged = null
        }
    }

    private fun createChild(
        config: Configuration,
        context: ComponentContext
    ): Child {
        return when (config) {
            is Configuration.DashboardScreen -> Child.DashBoardScreen(
                DashboardComponent(context) {
                    navigation.pop()
                }
            )

            is Configuration.LandingListScreen -> Child.LandingListScreen(
                LandingComponent(context, onBack = { navigation.pop() }) {
                    navigation.pushNew(Configuration.ServiceActorNameScreen(generateUniqueId()))
                }
            )

            is Configuration.HomeScreen -> Child.HomeScreen(
                HomeComponent(
                    context,
                    onNavigationToJoin = {
                        navigation.pushNew(Configuration.LandingListScreen(generateUniqueId()))
                    },
                    onNavigationToDashBoard = {
                        navigation.pushNew(Configuration.DashboardScreen(generateUniqueId()))
                    })
            )

            is Configuration.AddListingScreen -> Child.ServiceActorNameScreen(
                AddServiceNameComponent(
                    context,
                    onBack = { navigation.pop() },
                    onNavigationToAddressScreen = {
                        navigation.pushNew(Configuration.AddressScreen(generateUniqueId()))
                    }
                )
            )

            is Configuration.AddressScreen -> Child.AddressScreen(
                AddAddressComponent(
                    context,
                    onBack = { navigation.pop() },
                    onNavigationToUploadImagesScreen = {
                        navigation.pushNew(Configuration.AddImagesScreen(generateUniqueId()))
                    }
                )
            )

            is Configuration.AddImagesScreen -> Child.AddImagesScreen(
                AddImageComponent(
                    context,
                    onBack = { navigation.pop() },
                    onNavigationToDescriptionScreen = {
                        navigation.pushNew(Configuration.DescribeServiceScreen(generateUniqueId()))
                    }
                )
            )

            is Configuration.DescribeServiceScreen -> Child.DescribeServiceScreen(
                AddDescriptionServiceComponent(
                    context,
                    onBack = { navigation.pop() },
                    onNavigationToFinalDetailsScreen = {
                        navigation.pushNew(Configuration.FinalDetailsScreen(generateUniqueId()))
                    }
                )
            )

            is Configuration.FinalDetailsScreen -> Child.FinalDetailsScreen(
                AddFinalDetailsComponent(
                    context,
                    onBack = { navigation.pop() },
                    onNavigationToRequestReviewsScreen = {
                        navigation.pushNew(Configuration.RequestReviewsScreen(generateUniqueId()))
                    }
                )
            )

            is Configuration.RequestReviewsScreen -> Child.RequestReviewsScreen(
                AddRequestReviewComponent(
                    context,
                    onBack = { navigation.pop() },
                    onNavigationToAllDoneScreen = {
                        navigation.pushNew(Configuration.ChoosePlanScreen(generateUniqueId()))
                    }
                )
            )

            is Configuration.PriceTableScreen -> Child.PriceTableScreen(
                AddChoosePlanComponent(
                    context,
                    onBack = { navigation.pop() },
                    onNavigationToDashBoardScreen = {
                        navigation.pushNew(Configuration.RequestReviewsScreen(generateUniqueId()))
                    },
                    onNavigationGoToPaymentScreen = {
                        navigation.pushNew(Configuration.PaymentScreen(generateUniqueId()))
                    }
                ))

            is Configuration.ServiceActorNameScreen -> Child.ServiceActorNameScreen(
                AddServiceNameComponent(
                    context,
                    onBack = { navigation.pop() },
                    onNavigationToAddressScreen = {
                        navigation.pushNew(Configuration.AddressScreen(generateUniqueId()))
                    })
            )

            is Configuration.ChoosePlanScreen -> Child.ChoosePlanScreen(AddChoosePlanComponent(
                context,
                onBack = { navigation.pop() },
                onNavigationToDashBoardScreen = {
                    navigation.pushNew(Configuration.DashboardScreen(generateUniqueId()))
                },
                onNavigationGoToPaymentScreen = {
                    navigation.pushNew(Configuration.PaymentScreen(generateUniqueId()))
                }
            ))

            is Configuration.PaymentScreen -> Child.AddPaymentScreen(AddPaymentComponent(
                context,
                onBack = { navigation.pop() },
                onNavigationToDashboard = {
                    navigation.pushNew(Configuration.DashboardScreen(generateUniqueId()))
                }
            ))
        }
    }

    private fun updateUrl(configuration: Configuration) {
        val path = when (configuration) {
            is Configuration.HomeScreen -> "home"
            is Configuration.DashboardScreen -> "dashboard"
            is Configuration.LandingListScreen -> "unirte"
            is Configuration.AddListingScreen -> "add_listing"
            is Configuration.AddImagesScreen -> "add-image"
            is Configuration.AddressScreen -> "add-address"
            is Configuration.DescribeServiceScreen -> "describe-service"
            is Configuration.FinalDetailsScreen -> "final-details"
            is Configuration.PriceTableScreen -> "price-table"
            is Configuration.RequestReviewsScreen -> "request-reviews"
            is Configuration.ServiceActorNameScreen -> "service-actor-name"
            is Configuration.ChoosePlanScreen -> "choose-plan"
            is Configuration.PaymentScreen -> "payment"
        }
        urlHandler?.pushUrl(path)
    }

    private fun getInitialConfiguration(): Configuration {
        return when (urlHandler?.getPath()) {
            "dashboard" -> Configuration.DashboardScreen(uniqueId = generateUniqueId())
            "unirte" -> Configuration.LandingListScreen(uniqueId = generateUniqueId())
            else -> Configuration.HomeScreen(uniqueId = generateUniqueId())
        }
    }

    private fun handleUrlChange(path: String) {
        val newConfiguration = when (path) {
            "dashboard" -> Configuration.DashboardScreen(uniqueId = generateUniqueId())
            "unirte" -> Configuration.LandingListScreen(uniqueId = generateUniqueId())
            else -> Configuration.HomeScreen(uniqueId = generateUniqueId())
        }
        val currentConfiguration = childStack.value.active.configuration
        if (currentConfiguration != newConfiguration) {
            navigation.push(newConfiguration)
        }
    }

    private fun navigateTo(newConfiguration: Configuration) {
        val currentConfiguration = childStack.value.active.configuration
        if (currentConfiguration::class != newConfiguration::class) {
            navigation.push(newConfiguration)
        } else if (currentConfiguration != newConfiguration) {
            navigation.pop()
            navigation.push(newConfiguration)
        }
    }

    private fun generateUniqueId(): Int {
        return uniqueIdCounter++
    }
}