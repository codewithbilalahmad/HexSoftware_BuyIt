package com.muhammad.buyit.presentation.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.muhammad.buyit.data.UserSession
import com.muhammad.domain.repository.CategoryRepository
import com.muhammad.domain.repository.ProductRepository
import com.muhammad.domain.utils.onError
import com.muhammad.domain.utils.onSuccess
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeViewModel(
    private val productRepository: ProductRepository,
    private val userSession: UserSession,
    private val categoryRepository: CategoryRepository,
) : ViewModel() {
    private val _state = MutableStateFlow(HomeState())
    val state = _state.asStateFlow()

    init {
        onAction(HomeAction.GetUsername)
        onAction(HomeAction.GetHomeData)
    }

    fun onAction(action: HomeAction) {
        when (action) {
            HomeAction.GetCategories -> getCategories()
            HomeAction.GetFeaturedProducts -> getFeaturedProducts()
            HomeAction.GetHomeData -> getHomeData()
            HomeAction.GetPopularProducts -> getPopularProducts()
            HomeAction.GetUsername -> getUsername()
            is HomeAction.OnSearchChange -> {
                _state.update {
                    it.copy(searchQuery = action.search)
                }
            }

            HomeAction.GetElectronicsProducts -> getElectronicsProducts()
        }
    }

    private fun getUsername() {
        val user = userSession.getUser()
        _state.update {
            it.copy(username = user?.name.orEmpty())
        }
    }

    private fun getHomeData() {
        onAction(HomeAction.GetFeaturedProducts)
        onAction(HomeAction.GetPopularProducts)
        onAction(HomeAction.GetCategories)
        onAction(HomeAction.GetElectronicsProducts)
    }

    private fun getCategories() {
        viewModelScope.launch {
            categoryRepository.getCategories().onSuccess { data ->
                _state.update {
                    it.copy(
                        isCategoriesLoading = false,
                        categoriesError = null,
                        categories = data.categories
                    )
                }
            }.onError { error ->
                _state.update {
                    it.copy(
                        isCategoriesLoading = false,
                        categoriesError = error
                    )
                }
            }
        }
    }

    private fun getFeaturedProducts() {
        viewModelScope.launch {
            productRepository.getProducts(1).onSuccess { data ->
                _state.update {
                    it.copy(
                        isFeaturedProductsLoading = false,
                        featuredProductError = null,
                        featuredProducts = data.products
                    )
                }
            }.onError { error ->
                _state.update {
                    it.copy(
                        isFeaturedProductsLoading = false,
                        featuredProductError = error
                    )
                }
            }
        }
    }

    private fun getPopularProducts() {
        viewModelScope.launch {
            productRepository.getProducts(2).onSuccess { data ->
                _state.update {
                    it.copy(
                        isPopularProductsLoading = false,
                        popularProductsError = null,
                        popularProducts = data.products
                    )
                }
            }.onError { error ->
                _state.update {
                    it.copy(
                        isPopularProductsLoading = false,
                        popularProductsError = error
                    )
                }
            }
        }
    }

    private fun getElectronicsProducts() {
        viewModelScope.launch {
            productRepository.getProducts(3).onSuccess { data ->
                _state.update {
                    it.copy(
                        isElectronicsProductsLoading = false,
                        electronicsProductsError = null,
                        electronicsProducts = data.products
                    )
                }
            }.onError { error ->
                _state.update {
                    it.copy(
                        isElectronicsProductsLoading = false,
                        electronicsProductsError = error
                    )
                }
            }
        }
    }
}