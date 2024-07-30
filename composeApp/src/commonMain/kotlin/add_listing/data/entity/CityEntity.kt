package add_listing.data.entity

import add_listing.domain.model.CityModel
import kotlinx.serialization.Serializable

@Serializable
data class CityEntity(
    val id: Int,
    val name: String
)

fun CityEntity.toModel() = CityModel(id, name)