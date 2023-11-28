package com.dicoding.savemoney.ui.fragment.dashboard

import android.annotation.*
import android.content.*
import android.graphics.*
import android.os.*
import android.view.*
import android.widget.*
import androidx.fragment.app.*
import androidx.lifecycle.*
import com.dicoding.savemoney.*
import com.dicoding.savemoney.ui.add_expenditure.*
import com.github.mikephil.charting.charts.*
import com.github.mikephil.charting.components.*
import com.github.mikephil.charting.data.*
import com.github.mikephil.charting.formatter.*
import com.github.mikephil.charting.utils.*
import com.google.android.material.floatingactionbutton.*
import java.text.*
import java.util.*

@Suppress("DEPRECATION")
class DashboardFragment : Fragment() {
    private lateinit var dashboardViewModel: DashboardViewModel
    private lateinit var mLineChart: LineChart

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        dashboardViewModel = ViewModelProvider(this)[DashboardViewModel::class.java]
        val root = inflater.inflate(R.layout.fragment_dashboard, container, false)

        mLineChart = root.findViewById(R.id.chart)

        // to AddExpenditureActivity
        val fab: FloatingActionButton = root.findViewById(R.id.fab)
        fab.setOnClickListener {
            val intent = Intent(requireActivity(), AddExpenditureActivity::class.java)
            startActivity(intent)
        }

        // Mendapatkan tanggal, bulan, dan tahun saat ini
        val currentDate = Calendar.getInstance()


// Membuat daftar tanggal untuk sumbu X dengan format "dd-MMM" (tanggal-bulan)
// Mendapatkan tahun sekarang
        val dates = ArrayList<String>()
        val dateFormatter = SimpleDateFormat("dd-MMM", Locale.getDefault())

// Mendapatkan tanggal awal dan akhir bulan
        val firstDayOfMonth = currentDate.getActualMinimum(Calendar.DAY_OF_MONTH)
        val lastDayOfMonth = currentDate.getActualMaximum(Calendar.DAY_OF_MONTH)

        for (i in firstDayOfMonth..lastDayOfMonth) {
            // Mendapatkan tanggal ke-i dari bulan saat ini
            val date = currentDate.clone() as Calendar
            date.set(Calendar.DAY_OF_MONTH, i)
            dates.add(dateFormatter.format(date.time))
        }

// Membuat formatter untuk sumbu X
        val axisDateFormatter = AxisDateFormatter(dates.toArray(arrayOfNulls<String>(dates.size)))
        mLineChart.xAxis?.valueFormatter = axisDateFormatter

// Data pemasukan dan pengeluaran untuk setiap hari dalam sebulan
        val dataPemasukan = ArrayList<Entry>()
        val dataPengeluaran = ArrayList<Entry>()

        for (i in firstDayOfMonth..lastDayOfMonth) {
            val date = currentDate.clone() as Calendar
            date.set(Calendar.DAY_OF_MONTH, i)

            // Simulasi data pemasukan dan pengeluaran harian
            val pemasukanHarian =
                Random().nextInt(500000) + 1000000  // Random antara 1000000 dan 1500000
            val pengeluaranHarian =
                Random().nextInt(500000) + 300000  // Random antara 300000 dan 800000

            dataPemasukan.add(Entry(date.timeInMillis.toFloat(), pemasukanHarian.toFloat()))
            dataPengeluaran.add(Entry(date.timeInMillis.toFloat(), pengeluaranHarian.toFloat()))
        }

        val pemasukanLineDataSet = LineDataSet(dataPemasukan, "Pemasukan")
        pemasukanLineDataSet.mode = LineDataSet.Mode.CUBIC_BEZIER
        pemasukanLineDataSet.color = Color.GREEN
        pemasukanLineDataSet.circleRadius = 5f
        pemasukanLineDataSet.setCircleColor(Color.GREEN)

        val pengeluaranDataSet = LineDataSet(dataPengeluaran, "Pengeluaran")
        pengeluaranDataSet.mode = LineDataSet.Mode.CUBIC_BEZIER
        pengeluaranDataSet.color = Color.BLUE
        pengeluaranDataSet.circleRadius = 5f
        pengeluaranDataSet.setCircleColor(Color.BLUE)

        // Pengaturan sumbu Y
        val leftAxis: YAxis = mLineChart.axisLeft
        leftAxis.valueFormatter =
            IAxisValueFormatter { value, _ ->
                String.format("%.0f", value)
            }

        // Pengaturan sumbu X
        val xAxis: XAxis = mLineChart.xAxis
        xAxis.valueFormatter =
            IAxisValueFormatter { value, _ ->
                String.format("%.0f", value)
            }

        // Menambahkan label tahun hanya sekali
        xAxis.labelCount = 1
        xAxis.position = XAxis.XAxisPosition.BOTTOM

        //Setup Legend
        val legend = mLineChart.legend
        legend.isEnabled = true
        legend.verticalAlignment = Legend.LegendVerticalAlignment.TOP
        legend.horizontalAlignment = Legend.LegendHorizontalAlignment.CENTER
        legend.orientation = Legend.LegendOrientation.HORIZONTAL
        legend.setDrawInside(false)

        // Menghilangkan sumbu Y yang ada di sebelah kanan
        mLineChart.axisRight.isEnabled = false

        // Menghilangkan deskripsi pada Chart
        mLineChart.description.isEnabled = false

        mLineChart.data = LineData(pemasukanLineDataSet, pengeluaranDataSet)
        mLineChart.animateXY(100, 500)

        return root
    }

    @Deprecated("Deprecated in Java")
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.appbar_dashboard_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    @Deprecated("Deprecated in Java")
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_notification -> {
                Toast.makeText(requireContext(), "Notification Menu Clicked", Toast.LENGTH_SHORT)
                    .show()
                true
            }

            R.id.menu_profile -> {
                Toast.makeText(requireContext(), "Profile Menu Clicked", Toast.LENGTH_SHORT).show()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }
}
