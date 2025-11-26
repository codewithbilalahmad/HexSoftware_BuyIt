package com.muhammad.data.repository

import com.muhammad.domain.model.AddressDomainModel
import com.muhammad.domain.model.OrdersListModel
import com.muhammad.domain.network.NetworkService
import com.muhammad.domain.repository.OrderRepository
import com.muhammad.domain.utils.Result

class OrderRepositoryImp(
    private val networkService: NetworkService
) : OrderRepository {
    override suspend fun placeOrder(
        addressDomainModel: AddressDomainModel,
        userId: Long,
    ): Result<Long> {
        return networkService.placeOrder(address = addressDomainModel, userId = userId)
    }

    override suspend fun getOrderList(userId: Long): Result<OrdersListModel> {
        return networkService.getOrderList(userId)
    }
}