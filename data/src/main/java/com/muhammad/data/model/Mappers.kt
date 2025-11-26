package com.muhammad.data.model

import com.muhammad.data.model.request.AddressDataModel
import com.muhammad.domain.model.AddressDomainModel
import com.muhammad.domain.model.Product

fun Product.toDataProductModel() = DataProductModel(
    id = id,
    title = title,
    price = price,
    categoryId = categoryId,
    description = description,
    image = image
)

fun AddressDomainModel.toAddressDataModel() = AddressDataModel(
    addressLine = addressLine, city = city, state = state, postalCode = postalCode, country = country
)
fun AddressDataModel.toAddressDomainModel() = AddressDomainModel(
    addressLine = addressLine, city = city, state = state, postalCode = postalCode, country = country
)