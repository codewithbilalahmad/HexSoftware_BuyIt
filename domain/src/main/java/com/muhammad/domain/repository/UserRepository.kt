package com.muhammad.domain.repository

import com.muhammad.domain.model.UserDomainModel
import com.muhammad.domain.utils.Result


interface UserRepository {
    suspend fun login(email: String, password: String): Result<UserDomainModel>
    suspend fun register(
        email: String,
        password: String,
        name: String
    ): Result<UserDomainModel>
}