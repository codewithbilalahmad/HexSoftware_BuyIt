package com.muhammad.buyit.presentation.screens.all_products

import com.muhammad.domain.model.Product

sealed interface AllProductsAction {
    data class AddProductToCart(val product : Product) : AllProductsAction
    data object GetAllProducts : AllProductsAction
}