package location.data.api

import io.ktor.client.statement.HttpResponse
import location.data.entity.AddressRequestEntity

interface LocationApi {
    suspend fun getAddressSuggestion(queryRequest: AddressRequestEntity): HttpResponse
}