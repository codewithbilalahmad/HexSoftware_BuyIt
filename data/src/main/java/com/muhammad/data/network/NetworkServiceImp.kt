package com.muhammad.data.network

import com.muhammad.data.model.request.AddToCartRequest
import com.muhammad.data.model.request.AddressDataModel
import com.muhammad.data.model.request.LoginRequest
import com.muhammad.data.model.request.RegisterRequest
import com.muhammad.data.model.response.CartResponse
import com.muhammad.data.model.response.CartSummaryResponse
import com.muhammad.data.model.response.CategoriesListResponse
import com.muhammad.data.model.response.OrdersListResponse
import com.muhammad.data.model.response.PlaceOrderResponse
import com.muhammad.data.model.response.ProductListResponse
import com.muhammad.data.model.response.UserAuthResponse
import com.muhammad.data.network.client.delete
import com.muhammad.data.network.client.get
import com.muhammad.data.network.client.post
import com.muhammad.data.network.client.put
import com.muhammad.domain.model.AddressDomainModel
import com.muhammad.domain.model.CartItemModel
import com.muhammad.domain.model.CartModel
import com.muhammad.domain.model.CartSummary
import com.muhammad.domain.model.CategoriesListModel
import com.muhammad.domain.model.OrdersListModel
import com.muhammad.domain.model.ProductListModel
import com.muhammad.domain.model.UserDomainModel
import com.muhammad.domain.model.request.AddCartRequestModel
import com.muhammad.domain.network.NetworkService
import com.muhammad.domain.utils.Result
import com.muhammad.domain.utils.map
import io.ktor.client.HttpClient

class NetworkServiceImp(private val httpClient: HttpClient) : NetworkService {
    override suspend fun getProducts(category: Int?): Result<ProductListModel> {
        val route = if (category == null) "products" else "products/category/$category"
        return httpClient.get<ProductListResponse>(route = route).map { it.toProductList() }
    }

    override suspend fun getCategories(): Result<CategoriesListModel> {
        return httpClient.get<CategoriesListResponse>(route = "categories")
            .map { it.toCategoriesList() }
    }

    override suspend fun addProductToCart(
        request: AddCartRequestModel,
        userId: Long,
    ): Result<CartModel> {
        return httpClient.post<AddToCartRequest, CartResponse>(
            route = "cart/${userId}",
            body = AddToCartRequest.fromCartRequestModel(request)
        ).map { it.toCartModel() }
    }

    override suspend fun getCart(userId: Long): Result<CartModel> {
        return httpClient.get<CartResponse>(route = "cart/$userId").map { it.toCartModel() }
    }

    override suspend fun updateQuantity(
        cartItemModel: CartItemModel,
        userId: Long,
    ): Result<CartModel> {
        return httpClient.put<AddToCartRequest, CartResponse>(
            route = "cart/$userId", body = AddToCartRequest(
                productId = cartItemModel.productId,
                quantity = cartItemModel.quantity
            )
        ).map { it.toCartModel() }
    }

    override suspend fun deleteItem(
        cartItemId: Int,
        userId: Long,
    ): Result<CartModel> {
        return httpClient.delete<CartResponse>(route = "cart/$userId/$cartItemId")
            .map { it.toCartModel() }
    }

    override suspend fun getCartSummary(userId: Long): Result<CartSummary> {
        return httpClient.get<CartSummaryResponse>(route = "checkout/$userId/summary")
            .map { it.toCartSummary() }
    }

    override suspend fun placeOrder(
        address: AddressDomainModel,
        userId: Long,
    ): Result<Long> {
        val body = AddressDataModel.fromDomainAddress(address)
        return httpClient.post<AddressDataModel, PlaceOrderResponse>(
            route = "orders/$userId",
            body = body
        ).map { order -> order.data.id }
    }

    override suspend fun getOrderList(userId: Long): Result<OrdersListModel> {
        return httpClient.get<OrdersListResponse>(route = "orders/$userId")
            .map { it.toDomainResponse() }
    }

    override suspend fun login(
        email: String,
        password: String,
    ): Result<UserDomainModel> {
        return httpClient.post<LoginRequest, UserAuthResponse>(
            route = "login",
            body = LoginRequest(email, password)
        )
            .map { it.data.toDomainModel() }
    }

    override suspend fun register(
        email: String,
        password: String,
        name: String,
    ): Result<UserDomainModel> {
        return httpClient.post<RegisterRequest, UserAuthResponse>(
            route = "signup",
            body = RegisterRequest(name = name, email = email, password =  password)
        ).map { it.data.toDomainModel() }
    }
}