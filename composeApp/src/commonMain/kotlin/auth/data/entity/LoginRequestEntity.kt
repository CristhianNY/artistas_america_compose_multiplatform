package auth.data.entity

import auth.domain.model.LoginRequest
import kotlinx.serialization.Serializable

@Serializable
data class LoginRequestEntity(val email: String, val password: String)

fun LoginRequest.toEntity() = LoginRequestEntity(email, password)