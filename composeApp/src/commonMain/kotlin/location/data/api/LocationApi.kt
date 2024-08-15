package location

import io.ktor.client.statement.HttpResponse

interface LocationApi {
    suspend fun getAddress(text:String): HttpResponse
}