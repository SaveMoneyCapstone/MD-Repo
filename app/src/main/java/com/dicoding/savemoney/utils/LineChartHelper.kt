package com.dicoding.savemoney.utils

import android.graphics.*
import com.dicoding.savemoney.data.response.*
import com.github.mikephil.charting.charts.*
import com.github.mikephil.charting.components.*
import com.github.mikephil.charting.data.*
import com.github.mikephil.charting.formatter.*

class LineChartHelper(private val lineChart: LineChart) {

    fun setupLineChart() {
        lineChart.description.isEnabled = false
        lineChart.legend.isEnabled = true

        val xAxis = lineChart.xAxis
        xAxis.position = XAxis.XAxisPosition.BOTTOM
        xAxis.setDrawGridLines(false)
        xAxis.granularity = 1f
        xAxis.valueFormatter = IndexAxisValueFormatter()

        val leftAxis = lineChart.axisLeft
        leftAxis.granularity = 1f
        leftAxis.valueFormatter = CustomYAxisValueFormatter()

        val rightAxis = lineChart.axisRight
        rightAxis.isEnabled = false

        lineChart.animateXY(100, 500)

        lineChart.axisLeft.textColor = Color.LTGRAY
        lineChart.axisLeft.textSize = 12F
        lineChart.axisRight.textColor = Color.LTGRAY
        lineChart.legend.textColor = Color.LTGRAY
        lineChart.xAxis.textColor = Color.LTGRAY
        lineChart.xAxis.textSize = 12F
    }

    fun createLineData(sahamDataList: List<RecomendationsItem>): LineData {
        // Create entries for close, change, and percent

        val openEntries = sahamDataList.mapIndexed {index, saham ->
            Entry(index.toFloat(), saham.open.toFloat())

        }

        val closeEntries = sahamDataList.mapIndexed { index, saham ->
            Entry(index.toFloat(), saham.close.toFloat())
        }

        val high = sahamDataList.mapIndexed { index, saham ->
            Entry(index.toFloat(), saham.high.toFloat())
        }

        val low = sahamDataList.mapIndexed { index, saham ->
            Entry(index.toFloat(), saham.low.toFloat())
        }

        val mean = sahamDataList.mapIndexed { index, saham ->
            Entry(index.toFloat(), saham.hasil_mean.toFloat())
        }


        val openDataSet = createLineDataSet(openEntries, "Open", Color.GRAY)
        val closeDataSet = createLineDataSet(closeEntries, "Close", Color.BLUE)
        val highDataSet = createLineDataSet(high, "High", Color.GREEN)
        val lowDataSet = createLineDataSet(low, "Low", Color.RED)
        val meanDataSet = createLineDataSet(mean, "Mean", Color.CYAN)


        // Combine LineDataSets into LineData
        return LineData(openDataSet, closeDataSet, highDataSet, lowDataSet, meanDataSet)
    }

    private fun createLineDataSet(entries: List<Entry>, label: String, color: Int): LineDataSet {
        val dataSet = LineDataSet(entries, label)
        dataSet.color = color
        dataSet.setDrawCircles(false)
        dataSet.valueTextColor = color
        return dataSet
    }
}
