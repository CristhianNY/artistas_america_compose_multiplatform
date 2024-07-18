package support

interface Session {

    suspend fun login(accessToken: String)

    suspend fun logout(): Boolean

    suspend fun refresh(): Boolean

    fun isLogged(): Boolean

    fun isTokenSaved(): Boolean

    fun getAccessToken(): String

    fun getRefreshToken(): String
}