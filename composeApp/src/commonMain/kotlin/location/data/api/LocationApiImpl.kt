package location

import BASE_URL
import io.ktor.client.HttpClient
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import io.ktor.http.ContentType
import io.ktor.http.contentType

class LocationApiImpl(private val httpClient: HttpClient) {
    suspend fun getAddress(text:String): HttpResponse {
        return httpClient.post(BASE_URL+"locations/search") {
            contentType(ContentType.Application.Json)
            setBody(text)
        }
    }
}