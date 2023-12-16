package com.dicoding.savemoney.ui.fragment.sahamtrending

import androidx.lifecycle.*
import com.dicoding.savemoney.data.*
import com.dicoding.savemoney.data.repository.*
import com.dicoding.savemoney.data.response.*
class SahamTrendingViewModel(private val repository: StockRecommendationRepository) : ViewModel() {
    fun fetchStockResponse(userData: UserData): LiveData<ResultState<RecommendationResponse>> {
        return repository.getStock(userData)
    }
}


