package org.artistasamerica.artistas.plugins

import com.typesafe.config.ConfigFactory
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.Application
import io.ktor.server.application.call
import io.ktor.server.auth.authenticate
import io.ktor.server.auth.jwt.JWTPrincipal
import io.ktor.server.auth.principal
import io.ktor.server.config.HoconApplicationConfig
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.get
import io.ktor.server.routing.post
import io.ktor.server.routing.routing
import org.artistasamerica.artistas.bff.FullUserInfoModel
import org.artistasamerica.artistas.bff.UserModel
import org.artistasamerica.artistas.data.db.DatabaseConnection
import org.artistasamerica.artistas.data.user.UserEntity
import org.artistasamerica.artistas.domain.error.ErrorMapper
import org.artistasamerica.artistas.domain.user.UserCredentials
import org.artistasamerica.artistas.domain.user.UserRequest
import org.artistasamerica.artistas.domain.user.UserResponse
import org.artistasamerica.artistas.util.TokenManager
import org.ktorm.dsl.eq
import org.ktorm.dsl.from
import org.ktorm.dsl.insert
import org.ktorm.dsl.map
import org.ktorm.dsl.mapNotNull
import org.ktorm.dsl.select
import org.ktorm.dsl.where
import org.mindrot.jbcrypt.BCrypt

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
                set(it.userStatus, 1)
                set(it.userType, 1)
            }

            if (result == SUCCESS_INSERT) {
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

        post("/login") {
            val userCredential = call.receive<UserCredentials>()

            val email = userCredential.email.toString()
            val password = userCredential.password

            val user = db.from(UserEntity).select().where {
                UserEntity.email eq email
            }.map {
                val id = it[UserEntity.id]
                val userEmail = it[UserEntity.email]!!
                val userPassword = it[UserEntity.password]!!
                val idToken = it[UserEntity.idToken].orEmpty()
                UserModel(id, userEmail, userPassword, idToken)
            }.firstOrNull()

            if (user == null) {
                val error = ErrorMapper.INVALID_EMAIL_OR_PASSWORD
                call.respond(HttpStatusCode.BadRequest, UserResponse(success = false, data = error))
                return@post
            }

            val doesPasswordMatch = BCrypt.checkpw(password, user.password)

            if (!doesPasswordMatch) {
                val error = ErrorMapper.INVALID_EMAIL_OR_PASSWORD
                call.respond(
                    HttpStatusCode.Unauthorized,
                    UserResponse(success = false, data = error)
                )
                return@post
            }

            val token = tokenManager.generateJWTToken(user)
            call.respond(HttpStatusCode.OK, UserResponse(success = true, data = token))
        }

        authenticate("auth-jwt") {
            get("/me") {
                val principal = call.principal<JWTPrincipal>()

                // Log para depuración
                if (principal == null) {
                    call.respond(
                        HttpStatusCode.Unauthorized,
                        UserResponse(success = false, data = "Missing principal")
                    )
                    return@get
                }

                val email = principal.payload.getClaim("email").asString()

                // Log para depuración
                if (email.isNullOrEmpty()) {
                    call.respond(
                        HttpStatusCode.BadRequest,
                        UserResponse(success = false, data = "Invalid email claim")
                    )
                    return@get
                }

                val user =
                    db.from(UserEntity).select().where { UserEntity.email eq email }.mapNotNull {
                        FullUserInfoModel(
                            id = it[UserEntity.id],
                            name = it[UserEntity.name],
                            lastName = it[UserEntity.lastName],
                            email = it[UserEntity.email],
                            idToken = it[UserEntity.idToken],
                            userStatus = it[UserEntity.userStatus],
                            userType = it[UserEntity.userType]
                        )
                    }.firstOrNull()

                user?.let {
                    call.respond(it)
                } ?: call.respond(
                    HttpStatusCode.InternalServerError,
                    UserResponse(success = false, data = "Error Getting Data")
                )
            }
        }
    }
}
