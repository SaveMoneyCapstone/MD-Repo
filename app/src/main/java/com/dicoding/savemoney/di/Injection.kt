package com.dicoding.savemoney.di

import com.dicoding.savemoney.data.api.*
import com.dicoding.savemoney.data.repository.*
import com.dicoding.savemoney.utils.*

object Injection {



    fun provideOjkInvestmentRepository(): OjkInvestmentRepository {
        val gateAPI2 = GateApi.API1
        val apiService = ApiConfig.getApiService( gateAPI2)
        return OjkInvestmentRepository.getInstance(apiService)
    }

    fun provideStockRepository(): StockRecommendationRepository {
        val gateAPI3 = GateApi.API3
        val apiService = ApiConfig.getApiService( gateAPI3)
        return StockRecommendationRepository.getInstance(apiService)
    }
    fun provideNewsRepository(): NewsRepository {
        val gateAPI3 = GateApi.API3
        val apiService = ApiConfig.getApiService( gateAPI3)
        return NewsRepository.getInstance(apiService)
    }
}