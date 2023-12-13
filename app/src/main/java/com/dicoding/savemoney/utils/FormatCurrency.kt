package com.dicoding.savemoney.utils

import java.text.*
import java.util.*

fun formatCurrency(amount: Int): String {
    val format = NumberFormat.getCurrencyInstance(Locale("id", "ID"))
    format.currency = Currency.getInstance("IDR")
    return format.format(amount.toLong())
}

fun formatCurrencyTransaction(amount: Double): String {
    val formatter = DecimalFormat.getCurrencyInstance() as DecimalFormat
    val customFormatSymbols = DecimalFormatSymbols(Locale("id", "ID"))
    formatter.decimalFormatSymbols = customFormatSymbols
    return formatter.format(amount)
}