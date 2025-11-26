package com.muhammad.buyit.presentation.screens.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.muhammad.buyit.R
import com.muhammad.buyit.presentation.components.AppTextField
import com.muhammad.buyit.presentation.navigation.BottomNavigationBar
import com.muhammad.buyit.presentation.navigation.Destination
import com.muhammad.buyit.presentation.screens.home.components.CategoriesSection
import com.muhammad.buyit.presentation.screens.home.components.HomeProductSection
import com.muhammad.buyit.presentation.screens.home.components.ProfileHeader
import com.muhammad.data.model.toDataProductModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun HomeScreen(navHostController: NavHostController, viewModel: HomeViewModel = koinViewModel()) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    Scaffold(modifier = Modifier.fillMaxSize(), bottomBar = {
        BottomNavigationBar(navController = navHostController)
    }){ paddingValues ->
        LazyColumn(
            modifier = Modifier.fillMaxSize().padding(paddingValues),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item(key = "profile_header") {
                ProfileHeader(username = state.username, onProductsScreen = {
                    navHostController.navigate(Destination.AllProductsScreen)
                })
            }
            item(key = "search_bar") {
                AppTextField(value = state.searchQuery, onValueChange = { newValue ->
                    viewModel.onAction(HomeAction.OnSearchChange(newValue))
                }, hint = R.string.search_products, leadingIcon = R.drawable.ic_search)
            }
            item(key = "categories_section") {
                CategoriesSection(
                    categories = state.categories,
                    categoriesError = state.categoriesError,
                    isCategoriesLoading = state.isCategoriesLoading
                )
            }
            item(key = "featured_products_section") {
                HomeProductSection(
                    isProductLoading = state.isFeaturedProductsLoading,
                    productError = state.featuredProductError,
                    products = state.featuredProducts,
                    onProductClick = { product ->
                        navHostController.navigate(Destination.ProductDetailScreen(product.toDataProductModel()))
                    },
                    title = R.string.featured, icon = R.drawable.featured
                )
            }
            item(key = "popular_products_section") {
                HomeProductSection(
                    isProductLoading = state.isPopularProductsLoading,
                    productError = state.popularProductsError,
                    products = state.popularProducts,
                    onProductClick = { product ->
                        navHostController.navigate(Destination.ProductDetailScreen(product.toDataProductModel()))
                    },
                    title = R.string.popular, icon = R.drawable.popular
                )
            }
            item(key = "electronics_products_section") {
                HomeProductSection(
                    isProductLoading = state.isElectronicsProductsLoading,
                    productError = state.electronicsProductsError,
                    products = state.electronicsProducts,
                    onProductClick = { product ->
                        navHostController.navigate(Destination.ProductDetailScreen(product.toDataProductModel()))
                    },
                    title = R.string.electronics, icon = R.drawable.electronics
                )
            }
        }
    }
}