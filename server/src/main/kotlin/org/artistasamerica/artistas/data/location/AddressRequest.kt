package org.artistasamerica.artistas.data.location

import kotlinx.serialization.Serializable

@Serializable
data class AddressRequest(val query: String)