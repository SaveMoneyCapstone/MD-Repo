package com.dicoding.savemoney.ui

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.savemoney.R
import com.dicoding.savemoney.ViewModelFactory
import com.dicoding.savemoney.adapter.NewsAdapter
import com.dicoding.savemoney.data.model.PostsItem
import com.dicoding.savemoney.databinding.ActivityNewsBinding
import com.dicoding.savemoney.ui.fragment.dashboard.DashboardViewModel

class NewsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityNewsBinding
    private val viewModel by viewModels<DashboardViewModel> {
        ViewModelFactory.getInstance()
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityNewsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.elevation = 0f
        supportActionBar?.title = ""
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        viewModel.isLoading().observe(this) {
            showLoading(it)
        }
        showNews()
    }
    private fun showNews() {
        viewModel.getNews()
        viewModel.news.observe(this) {
            listNews(it)
        }
    }
    private fun listNews(news: List<PostsItem>) {
        val adapter = NewsAdapter()
        binding.rvNews.layoutManager = LinearLayoutManager(this)
        binding.rvNews.setHasFixedSize(true)

        adapter.submitList(news)
        binding.rvNews.adapter =adapter
    }
    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return true
    }



}