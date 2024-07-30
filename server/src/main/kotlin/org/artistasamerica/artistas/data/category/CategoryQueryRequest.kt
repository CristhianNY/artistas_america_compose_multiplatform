package org.artistasamerica.artistas.data.category

import kotlinx.serialization.Serializable

@Serializable
data class CategoryQueryRequest(val query: String)