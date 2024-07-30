package add_listing.data.entity

import add_listing.domain.model.CityRequestModel
import kotlinx.serialization.Serializable

@Serializable
data class CityQueryRequest(val query: String)

fun CityRequestModel.toEntity() = CityQueryRequest(query)