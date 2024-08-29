package navigation.payment

sealed class AddPaymentEvent {
    data object GoBack : AddPaymentEvent()
    data object GoToDashboardScreen : AddPaymentEvent()
}