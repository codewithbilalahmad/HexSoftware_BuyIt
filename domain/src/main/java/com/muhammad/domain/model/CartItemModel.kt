package com.muhammad.domain.model

import androidx.compose.runtime.Immutable

@Immutable
data class CartItemModel(
    val id: Int,
    val productId: Int,
    val price: Double,
    val imageUrl: String?= null,
    val quantity: Int,
    val productName: String
)