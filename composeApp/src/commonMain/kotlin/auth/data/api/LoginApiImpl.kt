package auth.data.api

import auth.data.entity.LoginRequestEntity
import io.ktor.client.HttpClient
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import io.ktor.http.ContentType
import io.ktor.http.contentType

class LoginApiImpl(private val httpClient: HttpClient) : LoginApi {
    override suspend fun login(loginEntity: LoginRequestEntity): HttpResponse {
        return httpClient.post(" https://fc04-198-154-183-1.ngrok-free.app/login") {
            contentType(ContentType.Application.Json)
            setBody(loginEntity)
        }
    }
}
