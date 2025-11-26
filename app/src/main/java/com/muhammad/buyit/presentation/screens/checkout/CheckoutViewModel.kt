package com.muhammad.buyit.presentation.screens.checkout

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.muhammad.buyit.data.UserSession
import com.muhammad.domain.model.AddressDomainModel
import com.muhammad.domain.repository.CartRepository
import com.muhammad.domain.repository.OrderRepository
import com.muhammad.domain.utils.onError
import com.muhammad.domain.utils.onSuccess
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CheckoutViewModel(
    private val cartRepository: CartRepository,
    private val orderRepository: OrderRepository,
    private val userSession: UserSession,
) : ViewModel() {
    private val _state = MutableStateFlow(CheckoutState())
    val state = _state.asStateFlow()
    private val _events = Channel<CheckoutEvents>()
    val events = _events.receiveAsFlow()
    init {
        onAction(CheckoutAction.GetCartSummary)
    }
    fun onAction(action: CheckoutAction) {
        when (action) {
            CheckoutAction.GetCartSummary -> getCartSummary()
            is CheckoutAction.PlaceOrder -> placeOrder(action.address)
        }
    }

    private fun placeOrder(address: AddressDomainModel) {
        viewModelScope.launch {
            _state.update { it.copy(isPlacingOrder = true) }
            val userId = userSession.getUser()?.id ?: return@launch
            orderRepository.placeOrder(addressDomainModel = address, userId = userId.toLong())
                .onSuccess {
                    _events.trySend(CheckoutEvents.OnPlaceOrderSuccess)
                }.onError { error ->
                    _state.update { it.copy(isPlacingOrder = false, placeOrderError = error) }
                }
        }
    }

    private fun getCartSummary() {
        viewModelScope.launch {
            _state.update { it.copy(isCartSummaryLoading = true) }
            val userId = userSession.getUser()?.id ?: return@launch
            cartRepository.getCartSummary(userId.toLong()).onSuccess { data ->
                _state.update {
                    it.copy(
                        isCartSummaryLoading = false,
                        cartSummaryError = null,
                        cartSummary = data.data
                    )
                }
            }.onError { error ->
                _state.update {
                    it.copy(
                        isCartSummaryLoading = false,
                        cartSummaryError = error
                    )
                }
            }
        }
    }
}