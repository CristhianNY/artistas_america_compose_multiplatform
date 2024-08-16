package location.data.entity

import kotlinx.serialization.Serializable
import location.domain.model.MatchedSubstringModel

@Serializable
data class MatchedSubstringEntity(
    val length: Int?,
    val offset: Int?
)

fun MatchedSubstringEntity.toModel(): MatchedSubstringModel {
    return MatchedSubstringModel(
        length = this.length ?: 0,
        offset = this.offset ?: 0
    )
}