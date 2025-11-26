package com.muhammad.buyit.di

import com.muhammad.buyit.BuyItApplication
import com.muhammad.buyit.data.UserSession
import com.muhammad.buyit.presentation.screens.account.login.LoginViewModel
import com.muhammad.buyit.presentation.screens.account.register.RegisterViewModel
import com.muhammad.buyit.presentation.screens.all_products.AllProductsViewModel
import com.muhammad.buyit.presentation.screens.cart.CartViewModel
import com.muhammad.buyit.presentation.screens.checkout.CheckoutViewModel
import com.muhammad.buyit.presentation.screens.home.HomeViewModel
import com.muhammad.buyit.presentation.screens.orders.OrdersViewModel
import com.muhammad.buyit.presentation.screens.product_detail.ProductDetailViewModel
import com.muhammad.buyit.presentation.screens.profile.ProfileViewModel
import com.muhammad.buyit.presentation.screens.user_address.UserAddressViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val appModule = module {
    single { BuyItApplication.INSTANCE }
    single { UserSession(get()) }
    viewModelOf(::LoginViewModel)
    viewModelOf(::RegisterViewModel)
    viewModelOf(::HomeViewModel)
    viewModelOf(::ProductDetailViewModel)
    viewModelOf(::OrdersViewModel)
    viewModelOf(::AllProductsViewModel)
    viewModelOf(::CartViewModel)
    viewModelOf(::CheckoutViewModel)
    viewModelOf(::UserAddressViewModel)
    viewModelOf(::ProfileViewModel)
}