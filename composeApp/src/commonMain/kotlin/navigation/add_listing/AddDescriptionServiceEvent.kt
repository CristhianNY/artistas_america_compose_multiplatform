package navigation.add_listing

sealed class AddDescriptionServiceEvent {
    data object GoBack : AddDescriptionServiceEvent()
    data object GoToFinalDetailsScreen : AddDescriptionServiceEvent()
}