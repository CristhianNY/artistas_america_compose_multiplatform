package auth.data.api

import auth.data.entity.LoginEntity
import io.ktor.client.statement.HttpResponse

interface LoginApi {

    suspend fun login(): HttpResponse
}