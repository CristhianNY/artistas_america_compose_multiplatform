package org.artistasamerica.artistas.plugins

import io.ktor.server.application.Application
import io.ktor.server.application.call
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.post
import io.ktor.server.routing.routing
import org.artistasamerica.artistas.data.cities.CitiesEntity
import org.artistasamerica.artistas.data.cities.CitiesQueryRequest
import org.artistasamerica.artistas.data.cities.CitiesResponse
import org.artistasamerica.artistas.data.db.DatabaseConnection
import org.ktorm.dsl.from
import org.ktorm.dsl.like
import org.ktorm.dsl.mapNotNull
import org.ktorm.dsl.select
import org.ktorm.dsl.where

fun Application.citiesModule() {
    val db = DatabaseConnection.database

    routing {
        post("/cities/search") {
            val citiesQueryRequest = call.receive<CitiesQueryRequest>()
            val searchQuery = "%${citiesQueryRequest.query}%"

            val matchingCities = db.from(CitiesEntity)
                .select()
                .where { CitiesEntity.name like searchQuery }
                .mapNotNull { row ->
                    CitiesResponse(
                        id = row[CitiesEntity.id] ?: 0,
                        name = row[CitiesEntity.name] ?: ""
                    )
                }
                .toList()

            call.respond(matchingCities)
        }
    }
}