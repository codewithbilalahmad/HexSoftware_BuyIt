package com.muhammad.buyit.presentation.screens.checkout

import com.muhammad.domain.model.AddressDomainModel
import com.muhammad.domain.model.SummaryData

data class CheckoutState(
    val userAddress: AddressDomainModel = AddressDomainModel(
        addressLine = "Chakwal road, Talagang",
        city = "Talagang", state = "Pakistan", postalCode = "450238", country = "Pakistan"
    ),
    val cartSummary: SummaryData? = null,
    val isCartSummaryLoading: Boolean = false,
    val cartSummaryError: String? = null,
    val isPlacingOrder: Boolean = false,
    val placeOrderError: String? = null,
)
