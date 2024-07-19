package auth.data.data_source

import auth.data.api.LoginApi
import auth.data.entity.LoginEntity
import support.BaseDataSource
import support.Result

class AuthDataSourceImpl(private val api: LoginApi) : AuthDataSource, BaseDataSource() {
    override suspend fun login(): Result<LoginEntity> = getResult {
        executeNetworkAction {
            api.login()
        }
    }
}