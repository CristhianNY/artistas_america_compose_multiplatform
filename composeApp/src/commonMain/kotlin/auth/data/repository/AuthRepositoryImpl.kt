package auth.data.repository

import auth.data.data_source.AuthDataSource
import auth.data.entity.toEntity
import auth.data.entity.toModel
import auth.domain.model.LoginModel
import auth.domain.model.LoginRequest
import auth.domain.repository.AuthRepository
import support.GenericErrorMapper
import support.ResultDomain
import support.baseResponseErrorHandler

class AuthRepositoryImpl(private val dataSource: AuthDataSource) : AuthRepository {
    override suspend fun login(loginRequest: LoginRequest): ResultDomain<LoginModel> =
        baseResponseErrorHandler(
            GenericErrorMapper, dataSource.login(loginRequest.toEntity())
        ) { ResultDomain.Success(it.toModel()) }
}