package com.muhammad.buyit.presentation.screens.product_detail

import com.muhammad.domain.model.Product

sealed interface ProductDetailAction{
    data class OnAddProductToCart(val product : Product) : ProductDetailAction
}