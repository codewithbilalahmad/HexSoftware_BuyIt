package com.muhammad.domain.model

import androidx.compose.runtime.Immutable

@Immutable
data class Category(
    val id: Int,
    val image: String,
    val title: String
)
