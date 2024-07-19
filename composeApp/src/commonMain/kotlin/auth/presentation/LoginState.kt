package auth.presentation

import support.ErrorDomain

sealed class LoginState() {
    data object Idle : LoginState()
    data object Loading : LoginState()
    data class Success(val token: String) : LoginState()
    data class Error(val error: ErrorDomain) : LoginState()
}