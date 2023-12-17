package com.dicoding.savemoney.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import com.dicoding.savemoney.R
import com.dicoding.savemoney.data.model.PostsItem
import com.dicoding.savemoney.data.model.TransactionModel

class DetailNewsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_news)

        val webView = findViewById<WebView>(R.id.webView)

        webView.settings.javaScriptEnabled = true

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "News"

        webView.webViewClient = object : WebViewClient() {
        }

        webView.webChromeClient = object : WebChromeClient() {
            override fun onJsAlert(view: WebView, url: String, message: String, result: android.webkit.JsResult): Boolean {
                Toast.makeText(this@DetailNewsActivity, message, Toast.LENGTH_LONG).show()
                result.confirm()
                return true
            }
        }
        val detail = intent.getParcelableExtra<PostsItem>(DetailNewsActivity.TITLE)
        if (detail != null) {
            webView.loadUrl(detail.link)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return true
    }

    companion object {
        val TITLE = "title"
    }
}