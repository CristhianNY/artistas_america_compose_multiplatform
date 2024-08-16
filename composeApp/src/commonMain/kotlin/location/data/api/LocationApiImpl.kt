package location.data.api

import BASE_URL
import io.ktor.client.HttpClient
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import io.ktor.http.ContentType
import io.ktor.http.contentType
import location.data.entity.AddressRequestEntity

class LocationApiImpl(private val httpClient: HttpClient) : LocationApi {
    override suspend fun getAddressSuggestion(queryRequest: AddressRequestEntity): HttpResponse {
        return httpClient.post(BASE_URL+"/location/autocomplete") {
            contentType(ContentType.Application.Json)
            setBody(queryRequest)
        }
    }
}
