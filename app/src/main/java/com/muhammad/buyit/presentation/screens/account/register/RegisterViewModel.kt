package com.muhammad.buyit.presentation.screens.account.register

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

class RegisterViewModel(
    private val userRepository: UserRepository,
    private val userSession: UserSession
) : ViewModel() {
    private val _state = MutableStateFlow(RegisterState())
    val state = _state.asStateFlow()
    private val _events = Channel<RegisterEvent>()
    val events = _events.receiveAsFlow()
    fun onAction(action: RegisterAction) {
        when (action) {
            RegisterAction.OnRegisterClick -> onRegisterClick()
            is RegisterAction.OnEmailChange -> {
                _state.update { it.copy(email = action.email) }
            }
            is RegisterAction.OnPasswordChange -> {
                _state.update { it.copy(password = action.password) }
            }

            is RegisterAction.OnUsernameChange -> {
                _state.update { it.copy(username = action.username) }
            }
        }
    }

    private fun onRegisterClick() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            userRepository.register(
                email = state.value.email,
                password = state.value.password,
                name = state.value.username
            ).onSuccess { data ->
                userSession.insertUser(data)
                _state.update { it.copy(isLoading = false, registerError = null, isError = false) }
                _events.trySend(RegisterEvent.OnRegisterSuccess)
            }.onError { error ->
                _state.update { it.copy(isLoading = false, registerError = error, isError = true) }
            }
        }
    }
}