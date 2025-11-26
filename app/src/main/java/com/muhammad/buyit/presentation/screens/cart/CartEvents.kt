package com.muhammad.buyit.presentation.screens.cart

sealed interface CartEvents {
    data class CartItemDeleteFailed(val error: String) : CartEvents
    data class CartIncrementQuantityFailed(val error: String) : CartEvents
    data class CartDecrementQuantityFailed(val error: String) : CartEvents
}