package com.dicoding.savemoney.ui.fragment.dashboard

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.dicoding.savemoney.data.ResultState
import com.dicoding.savemoney.data.repository.NewsRepository
import com.dicoding.savemoney.data.repository.StockRecommendationRepository
import com.dicoding.savemoney.data.response.UserData
import com.dicoding.savemoney.data.response.UserPredict
import com.dicoding.savemoney.data.response.predict.PredictResponse

class DashboardViewModel(private val repository: NewsRepository, private val stockRepo: StockRecommendationRepository) : ViewModel() {
    val news = repository.listNews
    fun getNews() = repository.getNews()
    fun isLoading() = repository.isLoading

    fun fetchPredictRecom(userData: UserPredict): LiveData<ResultState<PredictResponse>> {
        return stockRepo.getPredict(userData)
    }
}