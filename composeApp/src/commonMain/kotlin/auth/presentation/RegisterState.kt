package auth.presentation

import support.ErrorDomain

sealed class RegisterState {
    data object Idle : RegisterState()
    data object Loading : RegisterState()
    data class Error(val error: ErrorDomain) : RegisterState()
    data object Success : RegisterState()
}
