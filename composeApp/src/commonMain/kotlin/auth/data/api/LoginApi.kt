package auth.data.api

import auth.data.entity.LoginEntity
import auth.data.entity.LoginRequestEntity
import io.ktor.client.statement.HttpResponse

interface LoginApi {

    suspend fun login(loginEntity: LoginRequestEntity): HttpResponse
}