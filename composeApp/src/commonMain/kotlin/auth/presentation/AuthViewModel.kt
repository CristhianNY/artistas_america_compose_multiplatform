// commonMain/AuthViewModel.kt
package auth.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import auth.domain.model.LoginRequest
import auth.domain.repository.AuthRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import support.ResultDomain
import support.saveToken
import support.getToken

class AuthViewModel(private val authRepository: AuthRepository) : ViewModel() {
    private val _uiState = MutableStateFlow<LoginState>(LoginState.Idle)
    val uiState = _uiState.asStateFlow()

    private val _isLoggedIn = MutableStateFlow(getToken() != null)
    val isLoggedIn = _isLoggedIn.asStateFlow()

    fun login(email: String, password: String) {
        viewModelScope.launch {
            _uiState.value = LoginState.Loading
            val loginRequest = LoginRequest(email, password)
            when (val result = authRepository.login(loginRequest)) {
                is ResultDomain.Success -> {
                    val token = result.data?.data.orEmpty()
                    _uiState.value = LoginState.Success(token)
                    saveToken(token)
                    _isLoggedIn.value = true
                }

                is ResultDomain.Error -> {
                    _uiState.value = LoginState.Error(result.error)
                }
            }
        }
    }

    fun logout() {
        saveToken(null)
        _isLoggedIn.value = false
        _uiState.value = LoginState.Idle
    }
}
