package com.dicoding.savemoney.utils

import java.text.NumberFormat
import java.util.Locale

object RupiahConverter {

    fun convertToRupiah(number: Double?): String {
        val localeID =  Locale("in", "ID")
        val numberFormat = NumberFormat.getCurrencyInstance(localeID)
        numberFormat.minimumFractionDigits = 0
        val strFormat = numberFormat.format(number)
        return strFormat.toString()
    }
}