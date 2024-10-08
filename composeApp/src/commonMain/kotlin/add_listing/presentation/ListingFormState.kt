package add_listing.presentation

data class ListingFormState(
    val category: String? = null,
    val city: String? = null,
    val serviceName: String? = null,
    val phoneNumber: String? = null,
    val address: String? = null,
    val description: String? = null,
    val imageUri: String? = null,
    val selectedOption: String? = null,
    val selectedImage: ByteArray? = null,
    val clientsToReview:List<Pair<String, String>>? = emptyList()
)