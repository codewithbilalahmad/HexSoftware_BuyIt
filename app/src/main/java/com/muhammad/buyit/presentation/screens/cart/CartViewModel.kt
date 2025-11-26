package com.muhammad.buyit.presentation.screens.cart

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.muhammad.buyit.data.UserSession
import com.muhammad.domain.model.CartItemModel
import com.muhammad.domain.repository.CartRepository
import com.muhammad.domain.utils.onError
import com.muhammad.domain.utils.onSuccess
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CartViewModel(
    private val cartRepository: CartRepository,
    private val userSession: UserSession,
) : ViewModel() {
    private val _state = MutableStateFlow(CartState())
    val state = _state.asStateFlow()
    private val _events = Channel<CartEvents>()
    val events = _events.receiveAsFlow()
    init {
        onAction(CartAction.GetCartList)
    }
    fun onAction(action: CartAction) {
        when (action) {
            is CartAction.DecrementQuantity -> decrementQuantity(action.cartItem)
            is CartAction.DeleteCartItem -> deleteCartItem(action.cartItem)
            CartAction.GetCartList -> getCartList()
            is CartAction.IncrementQuantity -> incrementQuantity(action.cartItem)
        }
    }

    private fun getCartList() {
        viewModelScope.launch {
            _state.update { it.copy(isCartLoading = true) }
            val userId = userSession.getUser()?.id ?: return@launch
            cartRepository.getCart(userId = userId.toLong()).onSuccess { data ->
                _state.update {
                    it.copy(
                        isCartLoading = false,
                        cartError = null,
                        cartList = data.data
                    )
                }
            }.onError { error ->
                _state.update { it.copy(isCartLoading = false, cartError = error) }
            }
        }
    }

    private fun deleteCartItem(cartItem: CartItemModel) {
        viewModelScope.launch {
            val userId = userSession.getUser()?.id ?: return@launch
            cartRepository.deleteItem(cartItemId = cartItem.id, userId = userId.toLong())
                .onSuccess {
                    _state.update {
                        it.copy(
                            cartList = state.value.cartList.filter { cart -> cart.id != cartItem.id }
                        )
                    }
                }.onError { error ->
                    _events.trySend(CartEvents.CartItemDeleteFailed(error))
                }
        }
    }

    private fun incrementQuantity(cartItem: CartItemModel) {
        if (cartItem.quantity == 10) {
            return
        }
        updateQuality(cartItem.copy(quantity = cartItem.quantity + 1), true)
    }

    private fun decrementQuantity(cartItem: CartItemModel) {
        if (cartItem.quantity == 1) {
            return
        }
        updateQuality(cartItem.copy(quantity = cartItem.quantity - 1), false)
    }

    private fun updateQuality(cartItem: CartItemModel, isIncrement: Boolean) {
        viewModelScope.launch {
            val userId = userSession.getUser()?.id ?: return@launch
            _state.update {
                it.copy(
                    cartList = it.cartList.map { item ->
                        if (item.id == cartItem.id) {
                            cartItem
                        } else {
                            item
                        }
                    }
                )
            }
            cartRepository.updateQuantity(cartItemModel = cartItem, userId = userId.toLong())
                .onError { error ->
                    _state.update {
                        it.copy(
                            cartList = it.cartList.map { item ->
                                if (item.id == cartItem.id) {
                                    val oldQuantity =
                                        if (isIncrement) cartItem.quantity - 1 else cartItem.quantity + 1
                                    cartItem.copy(quantity = oldQuantity)
                                } else item
                            }
                        )
                    }
                    if (isIncrement) {
                        _events.trySend(CartEvents.CartIncrementQuantityFailed(error))
                    } else {
                        _events.trySend(CartEvents.CartDecrementQuantityFailed(error))
                    }
                }
        }
    }
}