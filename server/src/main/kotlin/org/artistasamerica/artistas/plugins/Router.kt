package org.artistasamerica.artistas.plugins

import io.ktor.server.application.Application
import io.ktor.server.application.call
import io.ktor.server.response.respondText
import io.ktor.server.routing.get
import io.ktor.server.routing.routing

fun Application.configureRouting() {

    routing {
        get("/"){
            call.respondText {
                "Hola Cristhian"
            }
        }
    }
}