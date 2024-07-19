package auth.data.data_source

import auth.data.entity.LoginEntity
import support.Result

interface AuthDataSource {
    suspend fun login(): Result<LoginEntity>
}