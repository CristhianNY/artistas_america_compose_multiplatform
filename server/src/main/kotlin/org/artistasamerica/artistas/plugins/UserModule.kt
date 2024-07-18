package org.artistasamerica.artistas.plugins

import com.typesafe.config.ConfigFactory
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.Application
import io.ktor.server.application.call
import io.ktor.server.config.HoconApplicationConfig
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.post
import io.ktor.server.routing.routing
import org.artistasamerica.artistas.data.db.DatabaseConnection
import org.artistasamerica.artistas.data.user.UserEntity
import org.artistasamerica.artistas.domain.user.UserRequest
import org.artistasamerica.artistas.domain.user.UserResponse
import org.artistasamerica.artistas.util.TokenManager
import org.ktorm.dsl.eq
import org.ktorm.dsl.from
import org.ktorm.dsl.insert
import org.ktorm.dsl.map
import org.ktorm.dsl.select
import org.ktorm.dsl.where

const val SUCCESS_INSERT = 1
fun Application.userModule() {
    val db = DatabaseConnection.database
    val tokenManager = TokenManager(HoconApplicationConfig(ConfigFactory.load()))

    routing {
        post("register-user") {
            val request: UserRequest = call.receive<UserRequest>()

            val email = request.email.toString()
            val password = request.hashedPassword()

            val user = db.from(UserEntity).select().where { UserEntity.email eq email }
                .map { it[UserEntity.email] }
                .firstOrNull()


            if (user != null) {
                call.respond(
                    HttpStatusCode.BadRequest,
                    UserResponse(
                        success = true,
                        data = "User already exists, please try with another email"
                    )
                )
                return@post
            }

            val result = db.insert(UserEntity) {
                set(it.name, request.name)
                set(it.lastName, request.lastName)
                set(it.email, request.email)
                set(it.password, password)
            }

            if (result == SUCCESS_INSERT) {
                // sen successfully response to the client
                call.respond(
                    HttpStatusCode.OK,
                    UserResponse(success = true, data = "values has been successfully inserted")
                )
            } else {
                call.respond(
                    HttpStatusCode.BadRequest,
                    UserResponse(success = true, data = "Error Inserting")
                )
            }
        }
    }
}