package com.muhammad.buyit.presentation.screens.account.register

sealed interface RegisterAction {
    data class OnEmailChange(val email: String) : RegisterAction
    data class OnUsernameChange(val username: String) : RegisterAction
    data class OnPasswordChange(val password: String) : RegisterAction
    data object OnRegisterClick : RegisterAction
}