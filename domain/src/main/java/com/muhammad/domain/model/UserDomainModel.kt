package com.muhammad.domain.model

import androidx.compose.runtime.Immutable

@Immutable
data class UserDomainModel(
    val id: Int?,
    val username: String,
    val email: String,
    val name: String
)
