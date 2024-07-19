package auth.domain.repository

import auth.domain.model.LoginModel
import auth.domain.model.LoginRequest
import support.ResultDomain

interface AuthRepository {
    suspend fun login(loginRequest: LoginRequest): ResultDomain<LoginModel>
}