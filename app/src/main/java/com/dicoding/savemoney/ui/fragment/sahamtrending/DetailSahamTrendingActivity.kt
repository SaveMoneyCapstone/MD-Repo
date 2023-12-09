package com.dicoding.savemoney.ui.fragment.sahamtrending

import com.dicoding.savemoney.utils.LineChartHelper
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import coil.*
import coil.transform.*
import com.dicoding.savemoney.*
import com.dicoding.savemoney.data.response.ResultsItemSaham
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

        val resultsItemSaham: ResultsItemSaham? = intent.getParcelableExtra(TAG)
        if (resultsItemSaham != null) {
            val lineData = lineChartHelper.createLineData(listOf(resultsItemSaham))

            lineChart.data = lineData

            lineChart.invalidate()
        }

        binding.tvName.text = resultsItemSaham?.company?.name
        binding.ivLogo.load(resultsItemSaham?.company?.logo) {
            crossfade(true)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return true
    }

    companion object {
         const val TAG = "resultsItemSaham"
    }
}
