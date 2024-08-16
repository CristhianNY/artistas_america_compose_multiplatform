package location.data.entity

import kotlinx.serialization.Serializable
import location.domain.model.TermModel

@Serializable
data class TermEntity(
    val offset: Int?,
    val value: String?
)

fun TermEntity.toModel(): TermModel {
    return TermModel(
        offset = this.offset ?: 0,
        value = this.value.orEmpty()
    )
}