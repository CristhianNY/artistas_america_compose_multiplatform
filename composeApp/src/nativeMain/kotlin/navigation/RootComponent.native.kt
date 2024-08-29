package navigation

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.pop
import com.arkivanov.decompose.router.stack.pushNew
import com.arkivanov.decompose.value.Value
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
    componentContext: ComponentContext, private val urlHandler: UrlHandler?
) : ComponentContext by componentContext {

    private val navigation = StackNavigation<Configuration>()

    actual val childStack: Value<ChildStack<Configuration, Child>> = childStack(
        source = navigation,
        serializer = Configuration.serializer(),
        initialConfiguration = Configuration.HomeScreen(generateUniqueId()),
        handleBackButton = true,
        childFactory = ::createChild
    )

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
                    navigation.pushNew(Configuration.ChoosePlanScreen(generateUniqueId()))
                },
                onNavigationGoToPaymentScreen = {
                    navigation.pushNew(Configuration.PaymentScreen(generateUniqueId()))
                }
            ))

            is Configuration.PaymentScreen -> Child.AddPaymentScreen(AddPaymentComponent(
                context,
                onBack = { navigation.pop() },
                onNavigationToDashboard = {
                    navigation.pushNew(Configuration.ChoosePlanScreen(generateUniqueId()))
                }
            ))
        }
    }

    private fun generateUniqueId(): Int {
        return uniqueIdCounter++
    }
}