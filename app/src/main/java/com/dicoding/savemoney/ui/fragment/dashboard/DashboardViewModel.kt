package com.dicoding.savemoney.ui.fragment.dashboard

import androidx.lifecycle.ViewModel
import com.dicoding.savemoney.data.repository.NewsRepository

class DashboardViewModel(private val repository: NewsRepository) : ViewModel() {
    val news = repository.listNews
    fun getNews() = repository.getNews()
    fun isLoading() = repository.isLoading
}