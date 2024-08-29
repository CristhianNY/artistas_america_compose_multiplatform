package navigation.add_listing

sealed class AddChoosePlanEvent {
    data object GoBack : AddChoosePlanEvent()
    data object GoToDashboardScreen : AddChoosePlanEvent()
    data object GoToPaymentScreen : AddChoosePlanEvent()
}