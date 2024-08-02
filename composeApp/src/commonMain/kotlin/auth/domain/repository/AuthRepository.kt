package auth.domain.repository

import auth.domain.model.LoginModel
import auth.domain.model.LoginRequest
import auth.domain.model.RegisterUserModel
import auth.domain.model.RegisterUserRequestModel
import support.ResultDomain

interface AuthRepository {
    suspend fun login(loginRequest: LoginRequest): ResultDomain<LoginModel>

    suspend fun register(registerUserRequest: RegisterUserRequestModel): ResultDomain<RegisterUserModel>

}