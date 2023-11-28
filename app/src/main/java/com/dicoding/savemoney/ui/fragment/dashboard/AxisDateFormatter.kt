package com.dicoding.savemoney.ui.fragment.dashboard

import com.github.mikephil.charting.components.*
import com.github.mikephil.charting.formatter.IAxisValueFormatter

class AxisDateFormatter(private val mValues: Array<String>) :  IAxisValueFormatter{
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