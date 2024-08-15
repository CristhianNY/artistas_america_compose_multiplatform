package location.domain

data class PredictionModel(
    val description: String,
    val place_id: String,
    val reference: String,
    val structured_formatting: StructuredFormattingModel,
    val terms: List<TermModel>,
    val types: List<String>
)