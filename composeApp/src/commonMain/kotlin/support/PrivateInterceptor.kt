package support

import io.ktor.client.plugins.api.createClientPlugin
import io.ktor.http.HttpStatusCode
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class PrivateInterceptor(
    private val session: Session,
    private val networkStatus: NetworkStatus
) {

    companion object {
        private const val HEADER_NAME_TOKEN = "Authorization"
        private const val HEADER_NAME_ACCEPT = "Accept"
        private const val HEADER_NAME_CONTENT = "Content-Type"
        private const val HEADER_VALUE_JSON = "application/json; charset=utf-8"
        private const val TOKEN_NOT_SEND = 701
        private const val TOKEN_EXPIRED = 302
        private const val AUTHORIZATION_HEADER_NOT_FOUND = 301
    }

    val plugin = createClientPlugin("PrivateInterceptor") {
        onRequest { request, _ ->
            if (!networkStatus.isConnected()) {
                throw NetworkException("No network connection")
            }

            request.headers.append(HEADER_NAME_ACCEPT, HEADER_VALUE_JSON)
            request.headers.append(HEADER_NAME_CONTENT, HEADER_VALUE_JSON)

            val token = session.getAccessToken().ifEmpty { session.getRefreshToken() }
            request.headers.append(HEADER_NAME_TOKEN, "Bearer $token")
        }

        onResponse { response ->
            when (response.status) {
                HttpStatusCode.OK -> {
                    // TODO: Invalidate caches
                }

                HttpStatusCode.Unauthorized -> {
                    GlobalScope.launch { session.logout() }
                }

                HttpStatusCode.Forbidden -> {
                }

                HttpStatusCode.PreconditionFailed -> {
                    // TODO: One session for channel
                }

                HttpStatusCode(TOKEN_NOT_SEND, "Token Not Sent") -> {
                    //  GlobalScope.launch { session.logout() }
                }

                HttpStatusCode(TOKEN_EXPIRED, "Token Expired") -> {
                    // GlobalScope.launch { session.logout() }
                }

                HttpStatusCode(
                    AUTHORIZATION_HEADER_NOT_FOUND,
                    "Authorization Header Not Found"
                ) -> {
                }
            }
        }
    }
}
