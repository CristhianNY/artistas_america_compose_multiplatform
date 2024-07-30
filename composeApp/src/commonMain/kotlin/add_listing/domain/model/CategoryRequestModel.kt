package add_listing.domain.model

import add_listing.data.entity.CategoryQueryRequest

data class CategoryRequestModel(val query: String)

fun CategoryRequestModel.toEntity() = CategoryQueryRequest(query)