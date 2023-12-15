package com.dicoding.savemoney.data.model

import com.dicoding.savemoney.utils.*
import java.util.*

data class TransactionModel(
    val id: String = "",
    val amount: Double = 0.0,
    val category: String = "",
    val note: String = "",
    val timestamp: Date? = null,
    val transactionType: TransactionType? = null
)


