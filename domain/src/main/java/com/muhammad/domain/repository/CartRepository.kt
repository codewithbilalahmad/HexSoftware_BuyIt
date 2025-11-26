package com.muhammad.domain.repository

import com.muhammad.domain.model.CartItemModel
import com.muhammad.domain.model.CartModel
import com.muhammad.domain.model.CartSummary
import com.muhammad.domain.model.request.AddCartRequestModel
import com.muhammad.domain.utils.Result


interface CartRepository {
    suspend fun addProductToCart(
        request: AddCartRequestModel, userId: Long
    ): Result<CartModel>
    suspend fun getCart(userId: Long): Result<CartModel>
    suspend fun updateQuantity(cartItemModel: CartItemModel, userId: Long): Result<CartModel>
    suspend fun deleteItem(cartItemId: Int, userId: Long): Result<CartModel>
    suspend fun getCartSummary(userId: Long): Result<CartSummary>
}