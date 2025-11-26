package com.muhammad.buyit.presentation.screens.all_products

import com.muhammad.domain.model.Product

data class AllProductsState(
    val products : List<Product> = emptyList(),
    val isProductsLoading : Boolean = false,
    val productError : String?=null,
    val isAddingProductToCart : Boolean = false,
    val addProductToCartError : String ?= null
)
