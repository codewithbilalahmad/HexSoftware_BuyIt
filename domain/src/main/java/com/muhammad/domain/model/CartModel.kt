package com.muhammad.domain.model

import androidx.compose.runtime.Immutable

@Immutable
data class CartModel (
    val data: List<CartItemModel>,
    val msg: String
)