package navigation.add_listing

sealed class AddImageEvent {
    data object GoBack : AddImageEvent()
    data object GotoDescriptionScreen : AddImageEvent()
}