package org.artistasamerica.artistas

import SERVER_PORT
import com.typesafe.config.ConfigFactory
import io.ktor.serialization.kotlinx.json.json
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.auth.Authentication
import io.ktor.server.auth.jwt.JWTPrincipal
import io.ktor.server.auth.jwt.jwt
import io.ktor.server.config.HoconApplicationConfig
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import io.ktor.server.plugins.contentnegotiation.ContentNegotiation
import org.artistasamerica.artistas.plugins.configureRouting
import org.artistasamerica.artistas.util.TokenManager

fun main() {
    embeddedServer(Netty, port = SERVER_PORT, host = "0.0.0.0") {
        val config = HoconApplicationConfig(ConfigFactory.load())
        val tokenManager = TokenManager(config)
        install(Authentication) {
            jwt {
                verifier(tokenManager.verifyJWTToken())
                realm = config.property("realm").getString()
                validate { jwtCredential ->
                    if (jwtCredential.payload.getClaim("email").asString().isNotEmpty()) {
                        JWTPrincipal(jwtCredential.payload)
                    } else {
                        null
                    }
                }
            }
        }
        install(ContentNegotiation) {
            json()
        }
        module()
    }.start(wait = true)
}

fun Application.module() {
    configureRouting()
}