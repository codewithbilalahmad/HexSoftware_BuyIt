package com.muhammad.buyit.presentation.screens.user_address

import androidx.compose.foundation.text.input.TextFieldState

data class UserAddressState(
    val addressLine: TextFieldState = TextFieldState(),
    val city: TextFieldState = TextFieldState(),
    val state: TextFieldState = TextFieldState(),
    val postalCode: TextFieldState = TextFieldState(),
    val country: TextFieldState = TextFieldState()
)