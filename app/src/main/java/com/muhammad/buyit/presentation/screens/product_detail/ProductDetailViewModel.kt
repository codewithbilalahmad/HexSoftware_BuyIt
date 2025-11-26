package com.muhammad.buyit.presentation.screens.product_detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.muhammad.buyit.data.UserSession
import com.muhammad.domain.model.Product
import com.muhammad.domain.model.request.AddCartRequestModel
import com.muhammad.domain.repository.CartRepository
import com.muhammad.domain.utils.onError
import com.muhammad.domain.utils.onSuccess
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ProductDetailViewModel(
    private val userSession: UserSession,
    private val cartRepository: CartRepository,
) : ViewModel() {
    private val _state = MutableStateFlow(ProductDetailState())
    val state = _state.asStateFlow()
    fun onAction(action: ProductDetailAction) {
        when (action) {
            is ProductDetailAction.OnAddProductToCart -> addProductToCart(action.product)
        }
    }
    private fun addProductToCart(product: Product) {
        viewModelScope.launch {
            _state.update { it.copy(addingProductToCart = true) }
            val userId = userSession.getUser()?.id ?: 0
            cartRepository.addProductToCart(
                request = AddCartRequestModel(
                    productId = product.id,
                    productName = product.title,
                    price = product.price,
                    quantity = 1, userId = userId
                ), userId = userId.toLong()
            ).onSuccess {
                _state.update {
                    it.copy(
                        addingProductToCart = false,
                        addedToCartSuccess = true,
                        addedToCartError = null
                    )
                }
            }.onError {error ->
                _state.update {
                    it.copy(
                        addingProductToCart = false,
                        addedToCartSuccess = false,
                        addedToCartError = error
                    )
                }
            }
        }
    }
}