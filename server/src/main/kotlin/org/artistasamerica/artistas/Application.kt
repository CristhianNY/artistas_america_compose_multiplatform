package org.artistasamerica.artistas

import SERVER_PORT
import com.typesafe.config.ConfigFactory
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpMethod
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
import io.ktor.server.plugins.cors.routing.CORS
import org.artistasamerica.artistas.plugins.configureRouting
import org.artistasamerica.artistas.plugins.userModule
import org.artistasamerica.artistas.util.TokenManager

fun main() {
    embeddedServer(Netty, port = SERVER_PORT, host = "0.0.0.0") {
        module()
    }.start(wait = true)
}

fun Application.module() {
    val config = HoconApplicationConfig(ConfigFactory.load())
    val tokenManager = TokenManager(config)

    install(Authentication) {
        jwt("auth-jwt") {
            verifier(tokenManager.verifyJWTToken())
            realm = config.property("jwt.realm").getString()
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

    install(CORS) {
        anyHost()
        allowHeader(HttpHeaders.ContentType)
        allowHeader(HttpHeaders.Authorization)
        allowMethod(HttpMethod.Options)
        allowMethod(HttpMethod.Put)
        allowMethod(HttpMethod.Delete)
        allowMethod(HttpMethod.Patch)
    }

    configureRouting()
    userModule()
}
