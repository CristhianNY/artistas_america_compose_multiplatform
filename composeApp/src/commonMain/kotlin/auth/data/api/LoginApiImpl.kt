package auth.data.api

import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.statement.HttpResponse

class LoginApiImpl(private val httpClient: HttpClient) : LoginApi {
    override suspend fun login(): HttpResponse {
        return httpClient.get("http://0.0.0.0:8080/login")
    }
}
