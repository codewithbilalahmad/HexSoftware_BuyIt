package com.muhammad.buyit.presentation.screens.all_products

sealed interface AllProductsEvents{
    data object ProductAddedToCartSuccess : AllProductsEvents
}