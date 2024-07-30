package add_listing.data.api

import add_listing.data.entity.CategoryQueryRequest
import io.ktor.client.statement.HttpResponse

interface CategoryApi {

    suspend fun getCategories(queryRequest: CategoryQueryRequest): HttpResponse
}