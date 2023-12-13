package com.dicoding.savemoney.data.model

import com.dicoding.savemoney.utils.*
import java.util.*

data class TransactionModel(
    val id: String,
    val amount: Double,
    val category: String,
    val note: String,
    val timestamp: Date?,
    val transactionType: TransactionType
)

