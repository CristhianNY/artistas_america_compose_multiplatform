package org.artistasamerica.artistas.domain.user

import kotlinx.serialization.Serializable
import org.mindrot.jbcrypt.BCrypt

@Serializable
data class UserRequest(
    val name: String?,
    val lastName: String?,
    val email: String?,
    val password: String?,
    val subscription: Int?,
    val idToken: String?
) {
    fun hashedPassword(): String {
        return BCrypt.hashpw(password, BCrypt.gensalt())
    }
}
