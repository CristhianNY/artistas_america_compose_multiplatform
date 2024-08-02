package auth.data.api

import auth.data.entity.LoginRequestEntity
import auth.data.entity.RegisterUserRequestEntity
import io.ktor.client.HttpClient
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import io.ktor.http.ContentType
import io.ktor.http.contentType

class AuthApiImpl(private val httpClient: HttpClient) : AuthApi {
    override suspend fun login(loginEntity: LoginRequestEntity): HttpResponse {
        return httpClient.post("https://901e-198-154-183-1.ngrok-free.app/login") {
            contentType(ContentType.Application.Json)
            setBody(loginEntity)
        }
    }

    override suspend fun register(registerUserRequestEntity: RegisterUserRequestEntity): HttpResponse {
        return httpClient.post("https://901e-198-154-183-1.ngrok-free.app/register-user") {
            contentType(ContentType.Application.Json)
            setBody(registerUserRequestEntity)
        }
    }
}
