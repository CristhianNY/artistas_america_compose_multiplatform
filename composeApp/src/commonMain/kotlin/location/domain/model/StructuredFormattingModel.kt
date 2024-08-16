package location.domain.model

data class StructuredFormattingModel(
    val main_text: String,
    val main_text_matched_substrings: List<MatchedSubstringModel>,
    val secondary_text: String
)