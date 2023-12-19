package com.dicoding.savemoney.ui.fragment.sahamtrending

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Build
import com.dicoding.savemoney.utils.LineChartHelper
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import coil.*
import coil.transform.*
import com.dicoding.savemoney.*
import com.dicoding.savemoney.data.response.RecomendationsItem
import com.dicoding.savemoney.databinding.ActivityDetailSahamTrendingBinding
import com.dicoding.savemoney.utils.DateConverter
import java.util.TimeZone

@Suppress("DEPRECATION")
class DetailSahamTrendingActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailSahamTrendingBinding
    private lateinit var lineChartHelper: LineChartHelper

    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailSahamTrendingBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.title = getString(R.string.grafik_saham)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val lineChart = binding.chartSaham
        lineChartHelper = LineChartHelper(lineChart)
        lineChartHelper.setupLineChart()

         val resultsItemStock: RecomendationsItem? = intent.getParcelableExtra(SYMBOL)
        if (resultsItemStock != null) {
            val lineData = lineChartHelper.createLineData(listOf(resultsItemStock))

            lineChart.data = lineData

            lineChart.invalidate()
        }

        with(binding) {
            volume.text = String.format("%.2fM", resultsItemStock?.volume?.toDouble()?.div(1000000)
                ?: 0)
            high.text = resultsItemStock?.high?.toFloat().toString()
            low.text = resultsItemStock?.low?.toFloat().toString()
            mean.text = resultsItemStock?.hasil_mean.toString()
            date.text = resultsItemStock?.date
            open.text = resultsItemStock?.open?.toFloat().toString()
            close.text = resultsItemStock?.close?.toFloat().toString()
            val percent = (resultsItemStock?.close?.minus(resultsItemStock.open))?.div(((resultsItemStock.close).toDouble()))
            val percentage = (percent)?.times(100)
            if (percentage != null) {
                if(percentage < 0) {
                    change.setTextColor(Color.RED)
                } else if (percentage == 0.0) {
                    change.setTextColor(Color.BLACK)
                } else {
                    change.setTextColor(Color.GREEN)
                }
                change.text = "%.2f".format(percentage).toString() + "%"
            }
        }

       binding.tvName.text = resultsItemStock?.companyName
        binding.ivLogo.load(resultsItemStock?.companyLogo) {
            crossfade(true)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return true
    }

    companion object {
         const val SYMBOL = "symbol"
    }
}
