package com.muhammad.buyit.utils

import java.util.Currency
import java.text.NumberFormat

object CurrencyUtils{
    fun formatPrice(price : Double, currency : String = "USD") : String{
        val format = NumberFormat.getCurrencyInstance()
        format.currency = Currency.getInstance(currency)
        return format.format(price)
    }
}