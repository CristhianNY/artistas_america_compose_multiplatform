package org.artistasamerica.artistas.data.category

import kotlinx.serialization.Serializable

@Serializable
data class CategoryResponse(val id: Int, val name: String)