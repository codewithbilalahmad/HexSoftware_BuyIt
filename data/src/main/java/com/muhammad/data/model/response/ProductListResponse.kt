package com.muhammad.data.model.response

import com.muhammad.data.model.DataProductModel
import com.muhammad.domain.model.ProductListModel
import kotlinx.serialization.Serializable

@Serializable
data class ProductListResponse(
    val `data`: List<DataProductModel>,
    val msg: String
) {
    fun toProductList() = ProductListModel(
        products = `data`.map { it.toProduct() },
        msg = msg
    )
}