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
        return httpClient.post("http://0.0.0.0:8081/login") {
            contentType(ContentType.Application.Json)
            setBody(loginEntity)
        }
    }
}
