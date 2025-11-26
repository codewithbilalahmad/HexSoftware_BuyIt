package com.muhammad.domain.model

import androidx.compose.runtime.Immutable

@Immutable
data class AddressDomainModel(
    val addressLine: String,
    val city: String,
    val state: String,
    val postalCode: String,
    val country: String
)
