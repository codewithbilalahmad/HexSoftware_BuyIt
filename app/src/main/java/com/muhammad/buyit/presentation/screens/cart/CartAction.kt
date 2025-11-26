package com.muhammad.buyit.presentation.screens.cart

import com.muhammad.domain.model.CartItemModel

sealed interface CartAction{
    data object GetCartList : CartAction
    data class IncrementQuantity(val cartItem : CartItemModel) : CartAction
    data class DecrementQuantity(val cartItem : CartItemModel) : CartAction
    data class DeleteCartItem(val cartItem : CartItemModel) : CartAction
}