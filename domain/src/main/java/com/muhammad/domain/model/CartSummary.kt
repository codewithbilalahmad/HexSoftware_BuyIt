package com.muhammad.domain.model

import androidx.compose.runtime.Immutable

@Immutable
data class CartSummary(
    val `data`: SummaryData,
    val msg: String
)