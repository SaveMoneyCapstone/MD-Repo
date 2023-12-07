@file:Suppress("DEPRECATION")

package com.dicoding.savemoney.utils

import com.github.mikephil.charting.components.*
import com.github.mikephil.charting.formatter.IAxisValueFormatter

@Suppress("DEPRECATION")
class AxisDateFormatter(private val mValues: Array<String>) :  IAxisValueFormatter{
    @Deprecated("Deprecated in Java")
    override fun getFormattedValue(value: Float, axis: AxisBase?): String {
        return if (value >= 0) {
            if (mValues.size > value.toInt()) {
                mValues[value.toInt()]
            } else ""
        } else {
            ""
        }
    }
}