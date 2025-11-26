package com.muhammad.domain.model

import androidx.compose.runtime.Immutable

@Immutable
data class Product(
    val id: Int,
    val title: String,
    val price: Double,
    val categoryId: Int,
    val description: String,
    val image: String
)