package com.muhammad.buyit.presentation.screens.home

sealed interface HomeAction{
    data object GetHomeData : HomeAction
    data object GetFeaturedProducts : HomeAction
    data object GetPopularProducts : HomeAction
    data object GetElectronicsProducts : HomeAction
    data object GetCategories : HomeAction
    data object GetUsername : HomeAction
    data class OnSearchChange(val search : String) : HomeAction
}