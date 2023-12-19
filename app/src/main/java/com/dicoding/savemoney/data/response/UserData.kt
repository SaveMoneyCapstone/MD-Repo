package com.dicoding.savemoney.data.response

data class UserData(
    val expense: List<Int>,
    val incomes: List<Int>
)

data class UserPredict(
    val expense: List<Int>
)