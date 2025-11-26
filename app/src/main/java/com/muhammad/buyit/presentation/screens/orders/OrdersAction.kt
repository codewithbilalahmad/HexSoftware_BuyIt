package com.muhammad.buyit.presentation.screens.orders

sealed interface OrdersAction {
    data object GetUserOrders : OrdersAction
    data class OnOrderFilter(val filter : String) : OrdersAction
}