package auth.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import auth.domain.model.LoginRequest
import auth.domain.repository.AuthRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import support.ResultDomain

class AuthViewModel(private val authRepository: AuthRepository) : ViewModel() {

    private val _uiState = MutableStateFlow<LoginState>(LoginState.Idle)
    val uiState = _uiState.asStateFlow()

    fun login(email: String, password: String) {
        viewModelScope.launch {
            _uiState.value = LoginState.Loading
            val loginRequest = LoginRequest(email, password)
            val result = authRepository.login(loginRequest)

            when (result) {
                is ResultDomain.Success -> {
                    _uiState.value = LoginState.Success(result.data?.data.orEmpty())
                }

                is ResultDomain.Error -> {
                    _uiState.value = LoginState.Error(result.error)
                }
            }
        }
    }
}
