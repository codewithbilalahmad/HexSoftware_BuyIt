package com.muhammad.buyit.presentation.screens.home

import com.muhammad.domain.model.Category
import com.muhammad.domain.model.Product

data class HomeState(
    val username : String = "",
    val searchQuery : String = "",
    val featuredProducts : List<Product> = emptyList(),
    val popularProducts : List<Product> = emptyList(),
    val electronicsProducts : List<Product> = emptyList(),
    val categories : List<Category> = emptyList(),
    val isFeaturedProductsLoading : Boolean = false,
    val featuredProductError : String? = null,
    val isPopularProductsLoading : Boolean = false,
    val popularProductsError : String? = null,
    val isElectronicsProductsLoading : Boolean = false,
    val electronicsProductsError : String? = null,
    val isCategoriesLoading : Boolean = false,
    val categoriesError : String? = null
)