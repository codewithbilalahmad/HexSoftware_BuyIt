package com.muhammad.buyit.presentation.screens.account.login

sealed interface LoginEvent{
    data object OnLoginSuccess : LoginEvent
}