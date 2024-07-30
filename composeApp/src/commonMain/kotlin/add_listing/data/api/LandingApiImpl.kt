package add_listing.data.api

import add_listing.data.entity.CategoryQueryRequest
import add_listing.data.entity.CityQueryRequest
import io.ktor.client.HttpClient
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import io.ktor.http.ContentType
import io.ktor.http.contentType

class LandingApiImpl(private val httpClient: HttpClient) : LandingApi {
    override suspend fun getCategories(queryRequest: CategoryQueryRequest): HttpResponse {
        return httpClient.post(" https://fc04-198-154-183-1.ngrok-free.app/categories/search") {
            contentType(ContentType.Application.Json)
            setBody(queryRequest)
        }
    }

    override suspend fun getCities(queryRequest: CityQueryRequest): HttpResponse {
        return httpClient.post(" https://fc04-198-154-183-1.ngrok-free.app/cities/search") {
            contentType(ContentType.Application.Json)
            setBody(queryRequest)
        }
    }
}