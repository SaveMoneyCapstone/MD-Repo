package com.dicoding.savemoney.ui.fragment.statistic

import android.annotation.*
import android.graphics.*
import android.os.*
import android.view.*
import android.widget.*
import androidx.fragment.app.*
import androidx.lifecycle.*
import com.dicoding.savemoney.*
import com.dicoding.savemoney.ui.ViewModelFactory
import com.dicoding.savemoney.ui.fragment.dashboard.*
import com.github.mikephil.charting.charts.*
import com.github.mikephil.charting.components.*
import com.github.mikephil.charting.data.*
import com.github.mikephil.charting.formatter.IAxisValueFormatter
import java.text.*
import java.util.*


class StatisticFragment : Fragment() {
    private lateinit var mLineChart: LineChart


    private val viewModel: StatisticViewModel by activityViewModels() {
        ViewModelFactory.getInstance(requireContext().applicationContext)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val root = inflater.inflate(R.layout.fragment_statistic, container, false)

        mLineChart = root.findViewById(R.id.chart)

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

        var firstDateMillis: Long = System.currentTimeMillis()
        var endDateMillis: Long = System.currentTimeMillis()

        val axisDateFormatter = AxisDateFormatter(dates.toArray(arrayOfNulls<String>(dates.size)))
        mLineChart.xAxis?.valueFormatter = axisDateFormatter

        val dataPemasukan = ArrayList<Entry>()
        val dataPengeluaran = ArrayList<Entry>()



        for (i in firstDayOfMonth..lastDayOfMonth) {
            val date = currentDate.clone() as Calendar
            date.set(Calendar.DAY_OF_MONTH, i)
            val first = date.timeInMillis
            var expensesday: Int
//            viewModel.getExpensesByDay(first).observe(viewLifecycleOwner) {
//                expensesday = it.toInt()
//                expensesday.
//            }
            val pemasukanHarian =
                Random().nextInt(500000) + 1000000

            val pengeluaranHarian =
                Random().nextInt(500000) + 300000

            dataPemasukan.add(Entry(date.timeInMillis.toFloat(), pemasukanHarian.toFloat()))

        }

        val pemasukanLineDataSet = LineDataSet(dataPemasukan, "Pemasukkan")
        pemasukanLineDataSet.mode = LineDataSet.Mode.CUBIC_BEZIER
        pemasukanLineDataSet.color = Color.GREEN
        pemasukanLineDataSet.circleRadius = 5f
        pemasukanLineDataSet.setCircleColor(Color.GREEN)

        val pengeluaranDataSet = LineDataSet(dataPengeluaran, "Pengeluaran")
        pengeluaranDataSet.mode = LineDataSet.Mode.CUBIC_BEZIER
        pengeluaranDataSet.color = Color.BLUE
        pengeluaranDataSet.circleRadius = 5f
        pengeluaranDataSet.setCircleColor(Color.BLUE)

        val leftAxis: YAxis = mLineChart.axisLeft
        leftAxis.valueFormatter =
            IAxisValueFormatter { value, _ ->
                String.format("%.0f", value)
            }

        val xAxis: XAxis = mLineChart.xAxis
        xAxis.valueFormatter =
            IAxisValueFormatter { value, _ ->
                String.format("%.0f", value)
            }

        xAxis.labelCount = 1
        xAxis.position = XAxis.XAxisPosition.BOTTOM

        val legend = mLineChart.legend
        legend.isEnabled = true
        legend.verticalAlignment = Legend.LegendVerticalAlignment.TOP
        legend.horizontalAlignment = Legend.LegendHorizontalAlignment.CENTER
        legend.orientation = Legend.LegendOrientation.HORIZONTAL
        legend.setDrawInside(false)

        mLineChart.axisRight.isEnabled = false

        mLineChart.description.isEnabled = false

        mLineChart.data = LineData(pemasukanLineDataSet, pengeluaranDataSet)
        mLineChart.animateXY(100, 500)

        return root

    }
}