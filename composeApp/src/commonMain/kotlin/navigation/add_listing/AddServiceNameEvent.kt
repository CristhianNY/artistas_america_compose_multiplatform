package navigation.add_listing

sealed class AddServiceNameEvent {
    data object GoBack : AddServiceNameEvent()
    data object GoToAddressScreen : AddServiceNameEvent()
}