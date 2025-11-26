package com.muhammad.buyit.presentation.screens.checkout

import com.muhammad.domain.model.AddressDomainModel

sealed interface CheckoutAction {
    data object GetCartSummary : CheckoutAction
    data class PlaceOrder(val address : AddressDomainModel) : CheckoutAction
}