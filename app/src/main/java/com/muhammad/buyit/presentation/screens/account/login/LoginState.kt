package com.muhammad.buyit.presentation.screens.account.login

data class LoginState(
    val email : String = "",
    val password : String = "",
    val isLoading : Boolean = false,
    val loginError : String?=null,
    val isPasswordVisible : Boolean = false,
    val isError : Boolean = false
)