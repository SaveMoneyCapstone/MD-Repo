package com.dicoding.savemoney.di

import com.dicoding.savemoney.data.api.*
import com.dicoding.savemoney.data.repository.*
import com.dicoding.savemoney.utils.*

object Injection {

    fun provideProfileCompanyRepository(): SahamTrendingRepository {
        val gateAPI1 = GateApi.API2
        val apiService = ApiConfig.getApiService(gateAPI1)
        return SahamTrendingRepository.getInstance(apiService)
    }

    fun provideOjkInvestmentRepository(): OjkInvestmentRepository {
        val gateAPI2 = GateApi.API1
        val apiService = ApiConfig.getApiService( gateAPI2)
        return OjkInvestmentRepository.getInstance(apiService)
    }
}