package com.dicoding.savemoney.ui.fragment.ojk

import android.annotation.*
import android.os.*
import android.webkit.*
import androidx.appcompat.app.*
import com.dicoding.savemoney.data.response.*
import com.dicoding.savemoney.databinding.*

@Suppress("DEPRECATION")
class DetailOjkInvestmentActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailOjkInvestmentBinding
    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailOjkInvestmentBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val ojkInvestmentDetail = intent.getParcelableExtra<AppsItem>(EXTRA_DATA) as AppsItem

        binding.webView.webViewClient = WebViewClient()
        binding.webView.settings.javaScriptEnabled = true
        binding.webView.loadUrl(ojkInvestmentDetail.url.toString())
    }

    companion object {
        const val EXTRA_DATA = "data"
    }
}