package auth.data.api

import auth.data.entity.LoginRequestEntity
import auth.data.entity.RegisterUserRequestEntity
import io.ktor.client.statement.HttpResponse

interface AuthApi {

    suspend fun login(loginEntity: LoginRequestEntity): HttpResponse

    suspend fun register(registerUserRequestEntity: RegisterUserRequestEntity): HttpResponse

}