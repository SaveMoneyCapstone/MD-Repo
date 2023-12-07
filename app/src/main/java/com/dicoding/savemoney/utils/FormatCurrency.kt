package com.dicoding.savemoney.utils

import java.text.*
import java.util.*

fun formatCurrency(amount: Int): String {
    val format = NumberFormat.getCurrencyInstance(Locale("id", "ID"))
    format.currency = Currency.getInstance("IDR")
    return format.format(amount.toLong())
}