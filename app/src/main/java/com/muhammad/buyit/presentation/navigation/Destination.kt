package com.muhammad.buyit.presentation.navigation

import com.muhammad.data.model.DataProductModel
import com.muhammad.data.model.request.AddressDataModel
import kotlinx.serialization.Serializable

sealed interface Destination{
    @Serializable
    data object HomeScreen : Destination
    @Serializable
    data object LoginScreen : Destination
    @Serializable
    data object RegisterScreen : Destination
    @Serializable
    data object AllProductsScreen : Destination
    @Serializable
    data object OrdersScreen : Destination
    @Serializable
    data object ProfileScreen : Destination
    @Serializable
    data object CartScreen : Destination
    @Serializable
    data object CheckoutScreen : Destination
    @Serializable
    data object OrderSuccessScreen : Destination
    @Serializable
    data class ProductDetailScreen(val product: DataProductModel) : Destination
    @Serializable
    data class UserAddressScreen(val userAddress : AddressDataModel) : Destination
}