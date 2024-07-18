package support

import io.ktor.client.call.body
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import io.ktor.http.isSuccess
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

abstract class BaseDataSource {

    var dispatcherProvider: CoroutineDispatcher = provideDispatcherProvider()

    protected suspend fun <T> executeNetworkAction(action: suspend () -> T): T =
        withContext(dispatcherProvider) { action() }

    protected suspend inline fun <reified T> getResult(call: () -> HttpResponse): Result<T> {
        var code = 0
        return try {
            val response = call()
            code = response.status.value

            if (response.status.isSuccess()) {
                val body: T = response.body()
                Result.success(body)
            } else {
                error(
                    Exception(response.status.description), code, response.bodyAsText()
                )
            }
        } catch (e: Exception) {
            error(e, code, "")
        }
    }

    fun <T> error(e: Exception, code: Int, errorBody: String, data: T? = null): Result<T> {
        return Result.error(e, code, errorBody, data)
    }
}
