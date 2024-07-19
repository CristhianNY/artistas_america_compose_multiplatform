package auth.domain.repository

import auth.domain.model.LoginModel
import support.ResultDomain

interface AuthRepository {
    suspend fun login(): ResultDomain<LoginModel>
}