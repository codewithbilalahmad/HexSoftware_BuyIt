package com.muhammad.buyit.presentation.screens.orders

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.muhammad.buyit.data.UserSession
import com.muhammad.domain.repository.OrderRepository
import com.muhammad.domain.utils.onError
import com.muhammad.domain.utils.onSuccess
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class OrdersViewModel(
    private val orderRepository: OrderRepository,
    private val userSession: UserSession,
) : ViewModel() {
    private val _state = MutableStateFlow(OrdersState())
    val state = _state.asStateFlow()
    init {
        onAction(OrdersAction.GetUserOrders)
    }
    fun onAction(action: OrdersAction) {
        when (action) {
            OrdersAction.GetUserOrders -> getUserOrders()
            is OrdersAction.OnOrderFilter -> onOrderFilter(action.filter)
        }
    }

    private fun onOrderFilter(filter: String) {
        if(filter == OrderFilter.All.name){
            _state.update { it.copy(orders = it.allOrders) }
            return
        }
        val filteredOrders = state.value.allOrders.filter { it.status == filter }
        _state.update { it.copy(orders = filteredOrders) }
    }

    private fun getUserOrders() {
        viewModelScope.launch {
            val userId = userSession.getUser()?.id ?: return@launch
            _state.update { it.copy(isOrdersLoading = true, ordersError = null) }
            orderRepository.getOrderList(userId = userId.toLong()).onSuccess { data ->
                _state.update {
                    it.copy(
                        allOrders = data.data,
                        orders = data.data,
                        isOrdersLoading = false,
                        ordersError = null
                    )
                }
            }.onError { error ->
                _state.update { it.copy(isOrdersLoading = false, ordersError = error) }
            }
        }
    }
}