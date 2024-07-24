package org.artistasamerica.artistas.bff

import kotlinx.serialization.Serializable


@Serializable
data class FullUserInfoModel(
    val id: Int?,
    val name: String?,
    val lastName: String?,
    val email: String?,
    val idToken: String?,
    val userStatus: Int?,
    val userType: Int?
)
