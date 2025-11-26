package com.muhammad.buyit.presentation.screens.account.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.muhammad.buyit.data.UserSession
import com.muhammad.domain.repository.UserRepository
import com.muhammad.domain.utils.onError
import com.muhammad.domain.utils.onSuccess
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LoginViewModel(
    private val userRepository: UserRepository,
    private val userSession: UserSession
) : ViewModel() {
    private val _state = MutableStateFlow(LoginState())
    val state = _state.asStateFlow()
    private val _events = Channel<LoginEvent>()
    val events = _events.receiveAsFlow()
    fun onAction(action: LoginAction) {
        when (action) {
            LoginAction.OnLoginClick -> onLoginClick()
            is LoginAction.OnEmailChange -> {
                _state.update { it.copy(email = action.email) }
            }
            is LoginAction.OnPasswordChange -> {
                _state.update { it.copy(password = action.password) }
            }

            LoginAction.OnPasswordToggle ->{
                _state.update { it.copy(isPasswordVisible = !it.isPasswordVisible) }
            }
        }
    }

    private fun onLoginClick() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            userRepository.login(
                email = state.value.email,
                password = state.value.password
            ).onSuccess { data ->
                userSession.insertUser(data)
                _state.update { it.copy(isLoading = false, loginError = null, isError = false) }
                _events.trySend(LoginEvent.OnLoginSuccess)
            }.onError { error ->
                _state.update { it.copy(isLoading = false, loginError = error, isError = true) }
            }
        }
    }
}