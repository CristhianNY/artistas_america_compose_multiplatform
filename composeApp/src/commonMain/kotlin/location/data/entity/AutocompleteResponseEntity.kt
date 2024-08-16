package location.data.entity

import kotlinx.serialization.Serializable
import location.domain.model.AutocompleteResponseModel

@Serializable
data class AutocompleteResponseEntity(
    val predictions: List<PredictionEntity>?,
    val status: String?
)

fun AutocompleteResponseEntity.toModel(): AutocompleteResponseModel {
    return AutocompleteResponseModel(
        predictions = this.predictions?.map { it.toModel() }.orEmpty(),
        status = this.status.orEmpty()
    )
}