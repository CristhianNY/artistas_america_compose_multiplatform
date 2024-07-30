package org.artistasamerica.artistas.data.cities

import kotlinx.serialization.Serializable

@Serializable
data class CitiesResponse(val id: Int, val name: String)