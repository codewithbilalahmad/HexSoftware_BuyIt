package com.muhammad.buyit.presentation.screens.product_detail

data class ProductDetailState(
    val addingProductToCart : Boolean = false,
    val addedToCartSuccess : Boolean = false,
    val addedToCartError : String? = null
)