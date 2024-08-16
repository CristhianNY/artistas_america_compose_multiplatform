package location.domain.model

data class AutocompleteResponseModel(
    val predictions: List<PredictionModel>,
    val status: String
)