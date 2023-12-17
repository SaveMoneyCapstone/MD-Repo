package com.dicoding.savemoney.ui.fragment.sahamtrending

import com.dicoding.savemoney.utils.LineChartHelper
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import coil.*
import coil.transform.*
import com.dicoding.savemoney.*
import com.dicoding.savemoney.data.response.RecomendationsItem
import com.dicoding.savemoney.databinding.ActivityDetailSahamTrendingBinding

@Suppress("DEPRECATION")
class DetailSahamTrendingActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailSahamTrendingBinding
    private lateinit var lineChartHelper: LineChartHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailSahamTrendingBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.title = getString(R.string.grafik_saham)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val lineChart = binding.chartSaham
        lineChartHelper = LineChartHelper(lineChart)
        lineChartHelper.setupLineChart()

         val resultsItemSaham: RecomendationsItem? = intent.getParcelableExtra(SYMBOL)
        if (resultsItemSaham != null) {
            val lineData = lineChartHelper.createLineData(listOf(resultsItemSaham))

            lineChart.data = lineData

            lineChart.invalidate()
        }

        with(binding) {
            volume.text = String.format("%.2fM", resultsItemSaham?.volume?.toDouble()?.div(1000000)
                ?: 0).toString()
            high.text = resultsItemSaham?.high?.toFloat().toString()
            low.text = resultsItemSaham?.low?.toFloat().toString()
            mean.text = resultsItemSaham?.hasil_mean.toString()
            date.text = resultsItemSaham?.date
        }

       binding.tvName.text = resultsItemSaham?.companyName
        binding.ivLogo.load(resultsItemSaham?.companyLogo) {
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
