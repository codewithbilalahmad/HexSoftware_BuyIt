package com.muhammad.buyit.presentation.screens.cart

import com.muhammad.domain.model.CartItemModel

data class CartState(
    val cartList : List<CartItemModel> = emptyList(),
    val isCartLoading : Boolean = false,
    val cartError : String?=null
)
