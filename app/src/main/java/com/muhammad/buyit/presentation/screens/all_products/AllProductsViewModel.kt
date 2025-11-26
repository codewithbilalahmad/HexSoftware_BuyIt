package com.muhammad.buyit.presentation.screens.all_products

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.muhammad.buyit.data.UserSession
import com.muhammad.domain.model.Product
import com.muhammad.domain.model.request.AddCartRequestModel
import com.muhammad.domain.repository.CartRepository
import com.muhammad.domain.repository.ProductRepository
import com.muhammad.domain.utils.onError
import com.muhammad.domain.utils.onSuccess
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class AllProductsViewModel(
    private val productRepository: ProductRepository,
    private val cartRepository: CartRepository,
    private val userSession: UserSession,
) : ViewModel() {
    private val _state = MutableStateFlow(AllProductsState())
    val state = _state.asStateFlow()
    private val _events = Channel<AllProductsEvents>()
    val events = _events.receiveAsFlow()
    init {
        onAction(AllProductsAction.GetAllProducts)
    }
    fun onAction(action: AllProductsAction) {
        when (action) {
            is AllProductsAction.AddProductToCart -> addProductToCart(action.product)
            AllProductsAction.GetAllProducts -> getAllProducts()
        }
    }

    private fun getAllProducts() {
        viewModelScope.launch {
            _state.update { it.copy(isProductsLoading = true) }
            productRepository.getProducts(null).onSuccess { data ->
                _state.update {
                    it.copy(
                        isProductsLoading = false,
                        productError = null,
                        products = data.products
                    )
                }
            }.onError { error ->
                _state.update {
                    it.copy(
                        isProductsLoading = false,
                        productError = error
                    )
                }
            }
        }
    }

    private fun addProductToCart(product: Product) {
        viewModelScope.launch {
            _state.update { it.copy(isAddingProductToCart = true) }
            val userId = userSession.getUser()?.id ?: return@launch
            cartRepository.addProductToCart(
                request = AddCartRequestModel(
                    productId = product.id,
                    productName = product.title,
                    price = product.price,
                    quantity = 1,
                    userId = userId
                ), userId = userId.toLong()
            ).onSuccess {
                _state.update {
                    it.copy(
                        isAddingProductToCart = false,
                        addProductToCartError = null
                    )
                }
                _events.trySend(AllProductsEvents.ProductAddedToCartSuccess)
            }.onError { error ->
                _state.update {
                    it.copy(
                        isAddingProductToCart = false,
                        addProductToCartError = error
                    )
                }
            }
        }
    }
}