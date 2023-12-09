package com.dicoding.savemoney.di

import android.content.*
import com.dicoding.savemoney.data.api.*
import com.dicoding.savemoney.data.preference.*
import com.dicoding.savemoney.data.repository.*
import com.dicoding.savemoney.utils.*

object Injection {
    fun provideAuthRepository(context: Context): AuthRepository {
        val userPreference = AuthPreference.getInstance(context.dataStore)
        val gateApiAuth = GateApi.API1
        val apiService = ApiConfig.getApiService(gateApiAuth)
        return AuthRepository.getInstanceAuth(apiService, userPreference)
    }

    fun provideProfileCompanyRepository(): SahamTrendingRepository {
        val gateAPI1 = GateApi.API2
        val apiService = ApiConfig.getApiService(gateAPI1)
        return SahamTrendingRepository.getInstance(apiService)
    }

    fun provideOjkInvestmentRepository(): OjkInvestmentRepository {
        val gateAPI2 = GateApi.API3
        val apiService = ApiConfig.getApiService(gateAPI2)
        return OjkInvestmentRepository.getInstance(apiService)
    }
}