package location.domain

data class AutocompleteResponseModel(
    val predictions: List<PredictionModel>,
    val status: String
)