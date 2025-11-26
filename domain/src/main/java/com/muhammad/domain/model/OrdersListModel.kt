package com.muhammad.domain.model

import androidx.compose.runtime.Immutable

@Immutable
data class OrdersListModel(
    val `data`: List<OrdersData>,
    val msg: String
)