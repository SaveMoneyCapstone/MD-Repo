package com.dicoding.savemoney.di

import android.content.*
import com.dicoding.savemoney.data.api.*
import com.dicoding.savemoney.data.local.database.*
import com.dicoding.savemoney.data.repository.*

object Injection {
    fun provideRepository(context: Context): ListBeiRepository {
        val apiService = ApiConfig.getApiService()
        val db = StockProductDatabase.getInstance(context)
        val dao = db.stockProductDao()
        return ListBeiRepository.getInstance(apiService)
    }

    fun provideProfileCompanyRepository(context: Context): CompanyProfileRepository {
        val apiService = ApiConfig.getApiService()
        val db = StockProductDatabase.getInstance(context)
        val dao = db.stockProductDao()
        return CompanyProfileRepository.getInstance(apiService)
    }
}