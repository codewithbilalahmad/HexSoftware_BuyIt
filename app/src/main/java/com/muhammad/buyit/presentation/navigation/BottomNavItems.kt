package com.muhammad.buyit.presentation.navigation

import androidx.annotation.StringRes
import com.muhammad.buyit.R

enum class BottomNavItems(
    val route: Destination,
    @get:StringRes val title: Int,
    val selectedIcon: Int,
    val unSelectedIcon: Int,
){
    Home(route = Destination.HomeScreen,
        title = R.string.home,
        selectedIcon = R.drawable.ic_home_filled,
        unSelectedIcon = R.drawable.ic_home_outlined
    ),
    Orders(
        route = Destination.OrdersScreen,
        title = R.string.orders,
        selectedIcon = R.drawable.order_filled,
        unSelectedIcon = R.drawable.order_outlined
    ),
    Cart(
        route = Destination.CartScreen,
        title = R.string.cart,
        selectedIcon = R.drawable.cart_filled,
        unSelectedIcon = R.drawable.cart_outlined
    ),
    Profile(
        route = Destination.ProfileScreen,
        title = R.string.profile,
        selectedIcon = R.drawable.user_filled,
        unSelectedIcon = R.drawable.user_outlined
    )
}

