package com.muhammad.domain.model

import androidx.compose.runtime.Immutable

@Immutable
data class OrderProductItem(
    val id: Int,
    val orderId: Int,
    val price: Double,
    val productId: Int,
    val productName: String,
    val quantity: Int,
    val userId: Int
)