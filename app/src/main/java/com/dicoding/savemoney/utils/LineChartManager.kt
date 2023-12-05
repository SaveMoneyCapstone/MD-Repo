package com.dicoding.savemoney.utils

import android.graphics.*
import com.dicoding.savemoney.ui.fragment.dashboard.*
import com.github.mikephil.charting.charts.*
import com.github.mikephil.charting.components.*
import com.github.mikephil.charting.data.*
import com.github.mikephil.charting.formatter.*
import java.text.*
import java.util.*

class LineChartManager(private val lineChart: LineChart) {

    fun setupLineChart() {
        val currentDate = Calendar.getInstance()
        val dates = ArrayList<String>()
        val dateFormatter = SimpleDateFormat("dd-MMM", Locale.getDefault())

        val firstDayOfMonth = currentDate.getActualMinimum(Calendar.DAY_OF_MONTH)
        val lastDayOfMonth = currentDate.getActualMaximum(Calendar.DAY_OF_MONTH)

        for (i in firstDayOfMonth..lastDayOfMonth) {
            val date = currentDate.clone() as Calendar
            date.set(Calendar.DAY_OF_MONTH, i)
            dates.add(dateFormatter.format(date.time))
        }

        val axisDateFormatter = AxisDateFormatter(dates.toArray(arrayOfNulls<String>(dates.size)))
        lineChart.xAxis?.valueFormatter = axisDateFormatter

        val dataPemasukan = ArrayList<Entry>()
        val dataPengeluaran = ArrayList<Entry>()

        for (i in firstDayOfMonth..lastDayOfMonth) {
            val date = currentDate.clone() as Calendar
            date.set(Calendar.DAY_OF_MONTH, i)

            val pemasukanHarian = Random().nextInt(500000) + 1000000
            val pengeluaranHarian = Random().nextInt(500000) + 300000

            dataPemasukan.add(Entry(date.timeInMillis.toFloat(), pemasukanHarian.toFloat()))
            dataPengeluaran.add(Entry(date.timeInMillis.toFloat(), pengeluaranHarian.toFloat()))
        }

        val pemasukanLineDataSet = LineDataSet(dataPemasukan, "Pemasukan")
        configureLineDataSet(pemasukanLineDataSet, Color.GREEN)

        val pengeluaranDataSet = LineDataSet(dataPengeluaran, "Pengeluaran")
        configureLineDataSet(pengeluaranDataSet, Color.BLUE)

        val leftAxis: YAxis = lineChart.axisLeft
        leftAxis.valueFormatter =
            IAxisValueFormatter { value, _ ->
                String.format("%.0f", value)
            }

        val xAxis: XAxis = lineChart.xAxis
        xAxis.valueFormatter =
            IAxisValueFormatter { value, _ ->
                String.format("%.0f", value)
            }

        xAxis.labelCount = 1
        xAxis.position = XAxis.XAxisPosition.BOTTOM

        val legend = lineChart.legend
        legend.isEnabled = true
        legend.verticalAlignment = Legend.LegendVerticalAlignment.TOP
        legend.horizontalAlignment = Legend.LegendHorizontalAlignment.CENTER
        legend.orientation = Legend.LegendOrientation.HORIZONTAL
        legend.setDrawInside(false)

        lineChart.axisRight.isEnabled = false

        lineChart.description.isEnabled = false

        lineChart.data = LineData(pemasukanLineDataSet, pengeluaranDataSet)
        lineChart.animateXY(100, 500)
    }

    private fun configureLineDataSet(lineDataSet: LineDataSet, color: Int) {
        lineDataSet.mode = LineDataSet.Mode.CUBIC_BEZIER
        lineDataSet.color = color
        lineDataSet.circleRadius = 5f
        lineDataSet.setCircleColor(color)
    }
}
