package navigation.add_listing

sealed class AddFinalDetailsEvent {
    data object GoBack : AddFinalDetailsEvent()
    data object GoToRequestReviewsScreen : AddFinalDetailsEvent()
}