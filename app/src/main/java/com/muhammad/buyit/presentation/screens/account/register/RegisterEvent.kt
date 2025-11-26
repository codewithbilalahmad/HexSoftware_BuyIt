package com.muhammad.buyit.presentation.screens.account.register

sealed interface RegisterEvent{
    data object OnRegisterSuccess : RegisterEvent
}