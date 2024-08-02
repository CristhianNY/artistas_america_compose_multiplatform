package auth.data.entity

import auth.domain.model.RegisterUserModel
import kotlinx.serialization.Serializable

@Serializable
data class RegisterUserEntity(
    val data: String,
    val success: Boolean
)

fun RegisterUserEntity.toModel() = RegisterUserModel(data, success)