package org.artistasamerica.artistas.data.cities

import kotlinx.serialization.Serializable

@Serializable
data class CitiesQueryRequest(val query: String)