package location.data.entity

import kotlinx.serialization.Serializable
import location.domain.model.StructuredFormattingModel

@Serializable
data class StructuredFormattingEntity(
    val main_text: String?,
    val main_text_matched_substrings: List<MatchedSubstringEntity>?,
    val secondary_text: String?
)

fun StructuredFormattingEntity.toModel(): StructuredFormattingModel {
    return StructuredFormattingModel(
        main_text = this.main_text.orEmpty(),
        main_text_matched_substrings = this.main_text_matched_substrings?.map { it.toModel() }.orEmpty(),
        secondary_text = this.secondary_text.orEmpty()
    )
}