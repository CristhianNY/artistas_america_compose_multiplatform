package location.data.entity

import kotlinx.serialization.Serializable
import location.domain.model.AddressRequestModel

@Serializable
data class AddressRequestEntity(val query: String)

fun AddressRequestModel.toEntity() = AddressRequestEntity(query)