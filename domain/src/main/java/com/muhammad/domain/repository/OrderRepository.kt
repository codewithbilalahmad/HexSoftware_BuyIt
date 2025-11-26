package com.muhammad.domain.repository

import com.muhammad.domain.model.AddressDomainModel
import com.muhammad.domain.model.OrdersListModel
import com.muhammad.domain.utils.Result


interface OrderRepository {
    suspend fun placeOrder(addressDomainModel: AddressDomainModel, userId: Long): Result<Long>
    suspend fun getOrderList(userId: Long): Result<OrdersListModel>
}