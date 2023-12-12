package com.dicoding.savemoney.data

import java.util.Date

// Transaction.kt
data class Transaction(var amount: Double ?= null, var category: String ?= null, var note: String ?= null, var timestamp: Date ?= null
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
