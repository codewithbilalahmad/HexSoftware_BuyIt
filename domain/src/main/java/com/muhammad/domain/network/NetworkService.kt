package com.muhammad.domain.network

import com.muhammad.domain.model.AddressDomainModel
import com.muhammad.domain.model.CartItemModel
import com.muhammad.domain.model.CartModel
import com.muhammad.domain.model.CartSummary
import com.muhammad.domain.model.CategoriesListModel
import com.muhammad.domain.model.OrdersListModel
import com.muhammad.domain.model.ProductListModel
import com.muhammad.domain.model.UserDomainModel
import com.muhammad.domain.model.request.AddCartRequestModel
import com.muhammad.domain.utils.Result


interface NetworkService {
    suspend fun getProducts(category: Int?): Result<ProductListModel>
    suspend fun getCategories(): Result<CategoriesListModel>

    suspend fun addProductToCart(
        request: AddCartRequestModel,
        userId: Long
    ): Result<CartModel>

    suspend fun getCart(userId: Long): Result<CartModel>
    suspend fun updateQuantity(cartItemModel: CartItemModel, userId: Long): Result<CartModel>
    suspend fun deleteItem(cartItemId: Int, userId: Long): Result<CartModel>
    suspend fun getCartSummary(userId: Long): Result<CartSummary>
    suspend fun placeOrder(address: AddressDomainModel, userId: Long): Result<Long>
    suspend fun getOrderList(userId: Long): Result<OrdersListModel>
    suspend fun login(email: String, password: String): Result<UserDomainModel>
    suspend fun register(
        email: String,
        password: String,
        name: String
    ): Result<UserDomainModel>
}