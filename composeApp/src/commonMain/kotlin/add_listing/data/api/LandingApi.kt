package add_listing.data.api

import add_listing.data.entity.CategoryQueryRequest
import add_listing.data.entity.CityQueryRequest
import io.ktor.client.statement.HttpResponse

interface LandingApi {

    suspend fun getCategories(queryRequest: CategoryQueryRequest): HttpResponse

    suspend fun getCities(queryRequest: CityQueryRequest): HttpResponse

}