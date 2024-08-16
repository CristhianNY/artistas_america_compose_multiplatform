package location.data.entity

import kotlinx.serialization.Serializable
import location.domain.model.PredictionModel
@Serializable
data class PredictionEntity(
    val description: String?,
    val place_id: String?,
    val reference: String?,
    val structured_formatting: StructuredFormattingEntity?,
    val terms: List<TermEntity>?,
    val types: List<String>?
)

fun PredictionEntity.toModel(): PredictionModel {
    return PredictionModel(
        description = this.description.orEmpty(),
        place_id = this.place_id.orEmpty(),
        reference = this.reference.orEmpty(),
        structured_formatting = this.structured_formatting?.toModel(),
        terms = this.terms?.map { it.toModel() }.orEmpty(),
        types = this.types.orEmpty()
    )
}