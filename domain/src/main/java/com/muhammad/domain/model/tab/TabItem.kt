package com.muhammad.domain.model.tab

import androidx.compose.runtime.Immutable

@Immutable
data class TabItem(
    val label: String,
    val selectedIcon: Int? = null,
    val unSelectedIcon: Int? = null,
)