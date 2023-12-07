package com.dicoding.savemoney.utils

import android.graphics.*
import com.github.mikephil.charting.charts.*
import com.github.mikephil.charting.components.*
import com.github.mikephil.charting.data.*
import com.github.mikephil.charting.formatter.*
import java.text.*
import java.util.*

class LineChartManager(private val lineChart: LineChart) {

    fun setupLineChart() {

        val dataPemasukan = listOf(1000000f, 1500000f, 1200000f, 2000000f, 1800000f)
        val dataPengeluaran = listOf(500000f, 700000f, 600000f, 800000f, 900000f)

        // Create LineDataSet for pemasukan
        val pemasukanLineDataSet = createLineDataSet(dataPemasukan, "Pemasukan", Color.GREEN)

        // Create LineDataSet for pengeluaran
        val pengeluaranDataSet = createLineDataSet(dataPengeluaran, "Pengeluaran", Color.BLUE)

        // Configure Y-axis to display values as integers
        val leftAxis: YAxis = lineChart.axisLeft
        leftAxis.valueFormatter = CustomYAxisValueFormatter()

        // Configure X-axis to display dates
        val xAxis: XAxis = lineChart.xAxis
        xAxis.valueFormatter = CustomXAxisValueFormatter()

        xAxis.labelCount = 1
        xAxis.position = XAxis.XAxisPosition.BOTTOM

        // Configure legend
        val legend = lineChart.legend
        legend.isEnabled = true
        legend.verticalAlignment = Legend.LegendVerticalAlignment.TOP
        legend.horizontalAlignment = Legend.LegendHorizontalAlignment.CENTER
        legend.orientation = Legend.LegendOrientation.HORIZONTAL
        legend.setDrawInside(false)

        lineChart.axisRight.isEnabled = false

        lineChart.description.isEnabled = false

        // Set LineData to the chart
        lineChart.data = LineData(pemasukanLineDataSet, pengeluaranDataSet)

        // Animate the chart
        lineChart.animateXY(100, 500)
    }

    private fun createLineDataSet(data: List<Float>, label: String, color: Int): LineDataSet {
        // Create entries for the LineDataSet
        val entries = data.mapIndexed { index, value -> Entry(index.toFloat(), value) }

        // Create LineDataSet
        val dataSet = LineDataSet(entries, label)
        dataSet.mode = LineDataSet.Mode.CUBIC_BEZIER
        dataSet.color = color
        dataSet.circleRadius = 5f
        dataSet.setCircleColor(color)

        return dataSet

    }


    private class CustomXAxisValueFormatter : ValueFormatter() {
        override fun getAxisLabel(value: Float, axis: AxisBase?): String {
            // Format the X-axis label as needed
            return SimpleDateFormat("dd-MMM", Locale.getDefault()).format(value.toLong())
        }
    }

    private class CustomYAxisValueFormatter : ValueFormatter() {
        override fun getAxisLabel(value: Float, axis: AxisBase?): String {
            // Format the Y-axis label as needed
            return value.toInt().toString()
        }

        override fun getFormattedValue(value: Float): String {
            // Format the Y-axis value as needed
            return value.toInt().toString()
        }
    }
}
