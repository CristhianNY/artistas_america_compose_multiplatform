package auth.data.data_source

import auth.data.api.AuthApi
import auth.data.entity.LoginEntity
import auth.data.entity.LoginRequestEntity
import auth.data.entity.RegisterUserEntity
import auth.data.entity.RegisterUserRequestEntity
import support.BaseDataSource
import support.Result

class AuthDataSourceImpl(private val api: AuthApi) : AuthDataSource, BaseDataSource() {
    override suspend fun login(loginEntity: LoginRequestEntity): Result<LoginEntity> = getResult {
        executeNetworkAction {
            api.login(loginEntity)
        }
    }
    override suspend fun register(registerUserRequestEntity: RegisterUserRequestEntity): Result<RegisterUserEntity> = getResult {
        executeNetworkAction {
            api.register(registerUserRequestEntity)
        }
    }
}