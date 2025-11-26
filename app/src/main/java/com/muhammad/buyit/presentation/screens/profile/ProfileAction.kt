package com.muhammad.buyit.presentation.screens.profile

sealed interface ProfileAction{
    data object GetUserData : ProfileAction
    data object OnLogout : ProfileAction
}