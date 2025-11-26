package com.muhammad.domain.model

import androidx.compose.runtime.Immutable

@Immutable
data class SummaryData(
    val discount: Double,
    val items: List<CartItemModel>,
    val shipping: Double,
    val subtotal: Double,
    val tax: Double,
    val total: Double
)