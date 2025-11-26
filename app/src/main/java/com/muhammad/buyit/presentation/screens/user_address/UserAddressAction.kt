package com.muhammad.buyit.presentation.screens.user_address

import com.muhammad.domain.model.AddressDomainModel

sealed interface UserAddressAction{
    data class GetAddressData(val address : AddressDomainModel) : UserAddressAction
}