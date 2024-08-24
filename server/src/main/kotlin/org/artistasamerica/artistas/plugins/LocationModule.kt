package org.artistasamerica.artistas.plugins

import io.ktor.server.application.Application
import io.ktor.server.application.call
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.post
import io.ktor.server.routing.get
import io.ktor.server.routing.routing
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonObject
import org.artistasamerica.artistas.data.cities.CitiesEntity
import org.artistasamerica.artistas.data.cities.CitiesQueryRequest
import org.artistasamerica.artistas.data.cities.CitiesResponse
import org.artistasamerica.artistas.data.db.DatabaseConnection
import org.artistasamerica.artistas.data.location.AddressRequest
import org.ktorm.dsl.from
import org.ktorm.dsl.like
import org.ktorm.dsl.mapNotNull
import org.ktorm.dsl.select
import org.ktorm.dsl.where
import java.net.HttpURLConnection
import java.net.URL
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

fun Application.locationModule() {
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

        post("/location/autocomplete") {
            val input = call.receive<AddressRequest>()
            val address = input.query
            val googleApiKey = "AIzaSyB8zpBi7sOPt55yWE6oxEoat3a0qlLMltI"

            if (address.isBlank()) {
                call.respond(mapOf("error" to "Input parameter is missing"))
                return@post
            }

            // Codifica el parámetro `input`
            val encodedInput = URLEncoder.encode(address, StandardCharsets.UTF_8.toString())
            val googleApiUrl = URL("https://maps.googleapis.com/maps/api/place/autocomplete/json?input=$encodedInput&key=$googleApiKey")
            val connection = googleApiUrl.openConnection() as HttpURLConnection

            try {
                connection.requestMethod = "GET"
                connection.connectTimeout = 5000
                connection.readTimeout = 5000

                val response = connection.inputStream.bufferedReader().use { it.readText() }
                val jsonResponse = Json.parseToJsonElement(response) as JsonObject

                call.respond(jsonResponse)
            } finally {
                connection.disconnect()
            }
        }
    }
}