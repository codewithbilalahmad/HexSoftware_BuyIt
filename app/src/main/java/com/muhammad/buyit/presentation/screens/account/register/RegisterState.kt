package com.muhammad.buyit.presentation.screens.account.register

data class RegisterState(
    val email : String = "",
    val password : String = "",
    val username : String = "",
    val isLoading : Boolean = false,
    val registerError : String?=null,
    val isError : Boolean = false
)