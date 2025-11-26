package com.muhammad.buyit.presentation.screens.user_address

import androidx.compose.foundation.text.input.TextFieldState
import androidx.lifecycle.ViewModel
import com.muhammad.domain.model.AddressDomainModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class UserAddressViewModel(
) : ViewModel() {
    private val _state = MutableStateFlow(UserAddressState())
    val state = _state.asStateFlow()
    fun onAction(action: UserAddressAction) {
        when (action) {
            is UserAddressAction.GetAddressData -> getAddressData(action.address)
        }
    }

    private fun getAddressData(address: AddressDomainModel) {
        _state.update {
            it.copy(
                addressLine = TextFieldState(address.addressLine),
                city = TextFieldState(address.city),
                state = TextFieldState(address.state),
                country = TextFieldState(address.country),
                postalCode = TextFieldState(address.postalCode)
            )
        }
    }
}