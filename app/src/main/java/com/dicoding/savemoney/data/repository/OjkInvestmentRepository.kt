package com.dicoding.savemoney.data.repository

import androidx.lifecycle.*
import com.dicoding.savemoney.data.*
import com.dicoding.savemoney.data.api.*
import kotlinx.coroutines.*

class OjkInvestmentRepository(private val apiService: ApiService) {

    fun getOjkInvestment() = liveData(Dispatchers.IO) {
        emit(ResultState.Loading)
        try {
            val response = apiService.getOjkInvestment()
            if (response.isSuccessful) {
                val ojkInvestmentResponse = response.body()
                if (ojkInvestmentResponse != null) {
                    emit(ResultState.Success(ojkInvestmentResponse))
                } else {
                    emit(ResultState.Error("Response body data is null"))
                }
            } else {
                emit(ResultState.Error("Error: ${response.code()}"))
            }
        } catch (e: Exception) {
            emit(ResultState.Error("Error: ${e.message}"))
        }
    }

    companion object {
        @Volatile
        private var instance: OjkInvestmentRepository? = null

        fun getInstance(apiService: ApiService): OjkInvestmentRepository {
            return instance ?: synchronized(this) {
                instance ?: OjkInvestmentRepository(apiService)
            }.also { instance = it }
        }
    }
}
