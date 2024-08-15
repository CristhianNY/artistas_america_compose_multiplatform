package navigation.add_listing

sealed interface AddListingEvent{
    data object GoBack : AddListingEvent
    data object GoToServiceNameScreen: AddListingEvent
}