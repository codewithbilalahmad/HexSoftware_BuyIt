package com.muhammad.buyit.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.muhammad.buyit.data.UserSession
import com.muhammad.buyit.presentation.screens.account.login.LoginScreen
import com.muhammad.buyit.presentation.screens.account.register.RegisterScreen
import com.muhammad.buyit.presentation.screens.all_products.AllProductScreen
import com.muhammad.buyit.presentation.screens.cart.CartScreen
import com.muhammad.buyit.presentation.screens.checkout.CheckoutScreen
import com.muhammad.buyit.presentation.screens.home.HomeScreen
import com.muhammad.buyit.presentation.screens.order_success.OrderSuccessScreen
import com.muhammad.buyit.presentation.screens.orders.OrdersScreen
import com.muhammad.buyit.presentation.screens.product_detail.ProductDetailScreen
import com.muhammad.buyit.presentation.screens.profile.ProfileScreen
import com.muhammad.buyit.presentation.screens.user_address.UserAddressScreen
import com.muhammad.data.model.DataProductModel
import com.muhammad.data.model.request.AddressDataModel
import com.muhammad.data.model.toAddressDomainModel
import kotlin.reflect.typeOf

@Composable
fun AppNavigation(
    navHostController: NavHostController,
    userSession: UserSession,
) {
    val startDestination = if (userSession.getUser() != null) Destination.HomeScreen else Destination.LoginScreen
    NavHost(navController = navHostController, startDestination = startDestination) {
        composable<Destination.LoginScreen> {
            LoginScreen(navHostController = navHostController)
        }
        composable<Destination.RegisterScreen> {
            RegisterScreen(navHostController = navHostController)
        }
        composable<Destination.HomeScreen> {
            HomeScreen(navHostController = navHostController)
        }
        composable<Destination.ProductDetailScreen>(typeMap = mapOf(
            typeOf<DataProductModel>() to CustomNavType.Product
        )) {
            val arguments = it.toRoute<Destination.ProductDetailScreen>()
            val product = arguments.product.toProduct()
            ProductDetailScreen(navHostController = navHostController, product = product)
        }
        composable<Destination.OrdersScreen>{
            OrdersScreen(navHostController = navHostController)
        }
        composable<Destination.AllProductsScreen>{
            AllProductScreen(navHostController = navHostController)
        }
        composable<Destination.CartScreen>{
            CartScreen(navHostController = navHostController)
        }
        composable<Destination.CheckoutScreen>{
            CheckoutScreen(navHostController = navHostController)
        }
        composable<Destination.OrderSuccessScreen>{
            OrderSuccessScreen(navHostController = navHostController)
        }
        composable<Destination.UserAddressScreen>(typeMap = mapOf(
            typeOf<AddressDataModel>() to CustomNavType.Address
        )){
            val arguments = it.toRoute<Destination.UserAddressScreen>()
            val userAddress = arguments.userAddress.toAddressDomainModel()
            UserAddressScreen(navHostController = navHostController, userAddress = userAddress)
        }
        composable<Destination.ProfileScreen>{
            ProfileScreen(navHostController = navHostController)
        }
    }
}