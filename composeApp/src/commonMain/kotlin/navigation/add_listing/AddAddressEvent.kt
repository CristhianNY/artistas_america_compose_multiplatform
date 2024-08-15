package navigation.add_listing

sealed class AddAddressEvent {
    data object GoBack : AddAddressEvent()
    data object GoToUploadImagesScreen : AddAddressEvent()
}