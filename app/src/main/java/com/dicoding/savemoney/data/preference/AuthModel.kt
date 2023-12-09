package com.dicoding.savemoney.data.preference

data class AuthModel(
    val email: String,
    val token: String,
    val isLogin: Boolean,
)