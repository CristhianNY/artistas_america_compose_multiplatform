package auth.data.entity


import auth.domain.model.LoginModel
import kotlinx.serialization.Serializable

@Serializable
data class LoginEntity(
    val data: String,
    val success: Boolean
)

fun LoginEntity.toModel() = LoginModel(data, success)