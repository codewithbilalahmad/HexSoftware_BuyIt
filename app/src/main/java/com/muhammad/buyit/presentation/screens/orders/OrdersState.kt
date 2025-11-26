package com.muhammad.buyit.presentation.screens.orders

import com.muhammad.domain.model.OrdersData

data class OrdersState(
    val allOrders: List<OrdersData> = emptyList(),
    val orders: List<OrdersData> = emptyList(),
    val ordersError: String? = null,
    val isOrdersLoading: Boolean = false
)