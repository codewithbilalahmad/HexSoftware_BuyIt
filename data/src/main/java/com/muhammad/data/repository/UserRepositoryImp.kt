package com.muhammad.data.repository

import com.muhammad.domain.model.UserDomainModel
import com.muhammad.domain.network.NetworkService
import com.muhammad.domain.repository.UserRepository
import com.muhammad.domain.utils.Result

class UserRepositoryImp(
    private val networkService: NetworkService
) : UserRepository {
    override suspend fun login(
        email: String,
        password: String,
    ): Result<UserDomainModel> {
        return networkService.login(email = email, password = password)
    }

    override suspend fun register(
        email: String,
        password: String,
        name: String,
    ): Result<UserDomainModel> {
        return networkService.register(email = email, password = password, name = name)
    }
}