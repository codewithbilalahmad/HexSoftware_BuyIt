package com.muhammad.domain.model

import androidx.compose.runtime.Immutable

@Immutable
data class ProductListModel(
    val products: List<Product>,
    val msg: String
)