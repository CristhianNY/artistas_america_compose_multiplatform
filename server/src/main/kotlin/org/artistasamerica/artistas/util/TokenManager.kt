package org.artistasamerica.artistas.util

import com.auth0.jwt.JWT
import com.auth0.jwt.JWTVerifier
import com.auth0.jwt.algorithms.Algorithm
import io.ktor.server.config.HoconApplicationConfig
import org.artistasamerica.artistas.bff.UserModel

class TokenManager(config: HoconApplicationConfig) {
    private val audience = config.property("jwt.audience").getString()
    private val secret = config.property("jwt.secret").getString()
    private val issuer = config.property("jwt.issuer").getString()

    fun generateJWTToken(user: UserModel): String {
        return JWT.create()
            .withAudience(audience)
            .withIssuer(issuer)
            .withClaim("email", user.email)
            .withClaim("userId", user.id)
            .sign(Algorithm.HMAC256(secret))
    }

    fun verifyJWTToken(): JWTVerifier {
        return JWT.require(Algorithm.HMAC256(secret))
            .withAudience(audience)
            .withIssuer(issuer)
            .build()
    }
}
