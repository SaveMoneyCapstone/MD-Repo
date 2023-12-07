package com.dicoding.savemoney.ui.saham

import android.content.*
import android.os.*
import android.view.*
import android.widget.*
import androidx.activity.*
import androidx.appcompat.app.*
import androidx.recyclerview.widget.*
import com.dicoding.savemoney.*
import com.dicoding.savemoney.R
import com.dicoding.savemoney.adapter.*
import com.dicoding.savemoney.data.*
import com.dicoding.savemoney.databinding.*

class SahamTrendingActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySahamTrendingBinding
    private lateinit var adapter: SahamTrendingAdapter
    private val viewModel: SahamTrendingViewModel by viewModels {
        ViewModelFactory.getInstance()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySahamTrendingBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.title = getString(R.string.saham_trending)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        adapter = SahamTrendingAdapter { resultsItemSaham ->
            val intent = Intent(this, LineChartSahamActivity::class.java)
            intent.putExtra(TAG, resultsItemSaham)
            startActivity(intent)
        }

        binding.rvSahamTrending.adapter = adapter

        binding.rvSahamTrending.layoutManager = LinearLayoutManager(this)

        viewModel.profileCompany.observe(this) { result ->
            when (result) {
                is ResultState.Loading -> {
                    isLoading(true)
                }

                is ResultState.Success -> {
                    isLoading(false)
                    showNoDataMessage(null)
                    adapter.submitList(result.data.data?.results)
                }

                is ResultState.Error -> {
                    isLoading(false)
                    showNoDataMessage(result.error)
                    Toast.makeText(this, result.error, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun isLoading(isLoading: Boolean) {
        binding.progresBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun showNoDataMessage(message: String?) {
        binding.tvMessage.apply {
            visibility = if (!message.isNullOrBlank()) View.VISIBLE else View.GONE
            text = message
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
