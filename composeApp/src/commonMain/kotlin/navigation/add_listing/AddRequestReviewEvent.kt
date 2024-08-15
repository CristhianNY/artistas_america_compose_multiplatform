package navigation.add_listing

sealed class AddRequestReviewEvent {
    data object GoBack : AddRequestReviewEvent()
    data object GoToAllDoneScreen : AddRequestReviewEvent()
}