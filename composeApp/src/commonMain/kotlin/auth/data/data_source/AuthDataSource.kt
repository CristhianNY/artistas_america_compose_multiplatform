package auth.data.data_source

import auth.data.entity.LoginEntity
import auth.data.entity.LoginRequestEntity
import support.Result

interface AuthDataSource {
    suspend fun login(loginEntity: LoginRequestEntity): Result<LoginEntity>
}