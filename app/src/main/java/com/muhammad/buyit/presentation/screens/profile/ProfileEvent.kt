package com.muhammad.buyit.presentation.screens.profile

sealed interface ProfileEvent{
    data object OnUserLogout : ProfileEvent
}