package com.muhammad.domain.model

import androidx.compose.runtime.Immutable

@Immutable
data class CategoriesListModel(
    val categories: List<Category>,
    val msg: String
)