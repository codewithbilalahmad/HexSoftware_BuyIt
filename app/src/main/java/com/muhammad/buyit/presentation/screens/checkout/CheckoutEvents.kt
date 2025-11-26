package com.muhammad.buyit.presentation.screens.checkout

sealed interface CheckoutEvents {
    data object OnPlaceOrderSuccess : CheckoutEvents
}