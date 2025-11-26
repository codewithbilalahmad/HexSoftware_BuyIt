package com.muhammad.domain.repository

import com.muhammad.domain.model.ProductListModel
import com.muhammad.domain.utils.Result

interface ProductRepository {
    suspend fun getProducts(category:Int?): Result<ProductListModel>
}