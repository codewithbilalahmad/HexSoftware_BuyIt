package com.muhammad.data.repository

import com.muhammad.domain.model.ProductListModel
import com.muhammad.domain.network.NetworkService
import com.muhammad.domain.repository.ProductRepository
import com.muhammad.domain.utils.Result

class ProductRepositoryImp(
    private val networkService: NetworkService
) : ProductRepository {
    override suspend fun getProducts(category: Int?): Result<ProductListModel> {
        return networkService.getProducts(category)
    }
}