package com.dicoding.savemoney.ui.saham

import com.dicoding.savemoney.utils.LineChartHelper
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import coil.*
import coil.transform.*
import com.dicoding.savemoney.*
import com.dicoding.savemoney.data.response.ResultsItemSaham
import com.dicoding.savemoney.databinding.ActivityLineChartSahamBinding

@Suppress("DEPRECATION")
class LineChartSahamActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLineChartSahamBinding
    private lateinit var lineChartHelper: LineChartHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLineChartSahamBinding.inflate(layoutInflater)
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

    override fun onNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return true
    }

    companion object {
        private const val TAG = "resultsItemSaham"
    }
}
