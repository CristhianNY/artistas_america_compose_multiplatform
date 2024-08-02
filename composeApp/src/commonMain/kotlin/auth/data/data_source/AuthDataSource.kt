package auth.data.data_source

import auth.data.entity.LoginEntity
import auth.data.entity.LoginRequestEntity
import auth.data.entity.RegisterUserEntity
import auth.data.entity.RegisterUserRequestEntity
import support.Result

interface AuthDataSource {
    suspend fun login(loginEntity: LoginRequestEntity): Result<LoginEntity>
    suspend fun register(registerUserRequestEntity: RegisterUserRequestEntity): Result<RegisterUserEntity>
}