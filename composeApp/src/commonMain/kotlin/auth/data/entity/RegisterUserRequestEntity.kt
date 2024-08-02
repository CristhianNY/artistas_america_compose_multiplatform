package auth.data.entity

import auth.domain.model.RegisterUserRequestModel
import kotlinx.serialization.Serializable

@Serializable
data class RegisterUserRequestEntity(val name: String, val lastName:String, val email: String, val password: String)

fun RegisterUserRequestModel.toEntity() = RegisterUserRequestEntity(name, lastName, email, password)