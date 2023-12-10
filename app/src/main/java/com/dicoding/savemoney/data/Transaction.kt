package com.dicoding.savemoney.data

// Transaction.kt
data class Transaction(
    val amount: Double,
    val category: String,
    val note: String,
    val timestamp: Long
)

// Expense.kt
data class Expense(
    val amount: Double,
    val category: String,
    val note: String,
    val timestamp: Long
)

// Income.kt
data class Income(
    val amount: Double,
    val category: String,
    val note: String,
    val timestamp: Long
)
