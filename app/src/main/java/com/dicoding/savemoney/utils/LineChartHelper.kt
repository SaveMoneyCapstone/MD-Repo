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
    }

    fun createLineData(sahamDataList: List<ResultsItemSaham>): LineData {
        // Create entries for close, change, and percent
        val closeEntries = sahamDataList.mapIndexed { index, saham ->
            Entry(index.toFloat(), saham.close!!.toFloat())
        }

        val changeEntries = sahamDataList.mapIndexed { index, saham ->
            Entry(index.toFloat(), saham.change!!.toFloat())
        }

        val percentEntries = sahamDataList.mapIndexed { index, saham ->
            Entry(index.toFloat(), saham.percent.toString().toFloat())
        }

        val closeDataSet = createLineDataSet(closeEntries, "Close", Color.BLUE)
        val changeDataSet = createLineDataSet(changeEntries, "Change", Color.RED)
        val percentDataSet = createLineDataSet(percentEntries, "Percent", Color.GREEN)

        // Combine LineDataSets into LineData
        return LineData(closeDataSet, changeDataSet, percentDataSet)
    }

    private fun createLineDataSet(entries: List<Entry>, label: String, color: Int): LineDataSet {
        val dataSet = LineDataSet(entries, label)
        dataSet.color = color
        dataSet.setDrawCircles(false)
        dataSet.valueTextColor = color
        return dataSet
    }
}
