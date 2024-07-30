package add_listing.data.entity


import add_listing.domain.model.CategoryModel
import kotlinx.serialization.Serializable

@Serializable
data class CategoryEntity(
    val id: Int,
    val name: String
)

fun CategoryEntity.toModel() = CategoryModel(id, name)