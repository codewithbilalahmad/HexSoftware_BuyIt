package com.muhammad.data.repository

import com.muhammad.domain.model.CartItemModel
import com.muhammad.domain.model.CartModel
import com.muhammad.domain.model.CartSummary
import com.muhammad.domain.model.request.AddCartRequestModel
import com.muhammad.domain.network.NetworkService
import com.muhammad.domain.repository.CartRepository
import com.muhammad.domain.utils.Result

class CartRepositoryImp(
    private val networkService: NetworkService
) : CartRepository{
    override suspend fun addProductToCart(
        request: AddCartRequestModel,
        userId: Long,
    ): Result<CartModel> {
        return networkService.addProductToCart(request, userId)
    }

    override suspend fun getCart(userId: Long): Result<CartModel> {
        return networkService.getCart(userId)
    }

    override suspend fun updateQuantity(
        cartItemModel: CartItemModel,
        userId: Long,
    ): Result<CartModel> {
        return networkService.updateQuantity(cartItemModel, userId)
    }

    override suspend fun deleteItem(
        cartItemId: Int,
        userId: Long,
    ): Result<CartModel> {
        return networkService.deleteItem(cartItemId, userId)
    }

    override suspend fun getCartSummary(userId: Long): Result<CartSummary> {
        return networkService.getCartSummary(userId)
    }

}