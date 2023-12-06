package com.dicoding.savemoney.di

import com.dicoding.savemoney.data.api.*
import com.dicoding.savemoney.data.repository.*
import com.dicoding.savemoney.utils.*

object Injection {

    fun provideRepository(): ListBeiRepository {
        val gateAPI1 = GateApi.API1
        val apiService = ApiConfig.getApiService(gateAPI1)
        return ListBeiRepository.getInstance(apiService)
    }

    fun provideProfileCompanyRepository(): CompanyProfileRepository {
        val gateAPI1 = GateApi.API1
        val apiService = ApiConfig.getApiService(gateAPI1)
        return CompanyProfileRepository.getInstance(apiService)
    }

    fun provideOjkInvestmentRepository(): OjkInvestmentRepository  {
        val gateAPI2 = GateApi.API2
        val apiService = ApiConfig.getApiService(gateAPI2)
        return OjkInvestmentRepository.getInstance(apiService)
    }
}