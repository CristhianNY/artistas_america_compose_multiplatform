package add_listing.data.entity

import kotlinx.serialization.Serializable

@Serializable
data class CategoryQueryRequest(val query: String)