package com.dicoding.savemoney.data

import com.dicoding.savemoney.utils.TransactionType
import java.util.Date

// Transaction.kt
data class Transaction(var id: String ?= null,var iconCode: Int ?= null, var amount: Double ?= null, var category: String ?= null, var note: String ?= null, var date: Date ?= null, val transactionType: TransactionType)

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
