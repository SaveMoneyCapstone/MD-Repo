package com.dicoding.savemoney.utils
import com.github.mikephil.charting.formatter.ValueFormatter
import com.github.mikephil.charting.components.AxisBase

class CustomYAxisValueFormatter : ValueFormatter() {

    override fun getAxisLabel(value: Float, axis: AxisBase?): String {
        // Format the Y-axis label as needed
        return value.toInt().toString()
    }

    override fun getFormattedValue(value: Float): String {
        // Format the Y-axis value as needed
        return value.toInt().toString()
    }
}
