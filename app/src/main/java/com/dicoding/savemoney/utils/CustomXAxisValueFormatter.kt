package com.dicoding.savemoney.utils

import android.annotation.SuppressLint
import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.formatter.ValueFormatter
import java.text.SimpleDateFormat
import java.util.*

class CustomXAxisValueFormatter(private val timestamps: List<Date>) : ValueFormatter() {

    @SuppressLint("SimpleDateFormat")
    override fun getAxisLabel(value: Float, axis: AxisBase?): String {
        val index = value.toInt()
        if (index >= 0 && index < timestamps.size) {
            val timestamp = timestamps[index]
            return SimpleDateFormat("dd-MMM").format(timestamp)
        }
        return ""
    }
}
