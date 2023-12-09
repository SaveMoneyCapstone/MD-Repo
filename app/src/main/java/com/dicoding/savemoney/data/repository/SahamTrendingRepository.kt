package com.dicoding.savemoney.data.repository

import androidx.lifecycle.*
import com.dicoding.savemoney.BuildConfig.API_KEY
import com.dicoding.savemoney.data.*
import com.dicoding.savemoney.data.api.*
import com.dicoding.savemoney.data.response.*

class SahamTrendingRepository(private val apiService: ApiService) {


    fun getSahamTrending(): LiveData<ResultState<SahamTrendingResponse>> =
        liveData {
            emit(ResultState.Loading)
            try {
                val response = apiService.getSahamTrending(API_KEY)
                if (response.isSuccessful) {
                    val sahamTrendingResponse = response.body()?.data
                    if (sahamTrendingResponse != null) {
                        val sahamResponse = SahamTrendingResponse(sahamTrendingResponse)
                        emit(ResultState.Success(sahamResponse))

                    } else {
                        emit(ResultState.Error("Response body data is null"))
                    }

                } else {
                    emit(ResultState.Error("Error: ${response.code()}"))
                }
            } catch (e: Exception) {
                ResultState.Error("Error: ${e.message}")
            }
        }

    companion object {
        @Volatile
        private var instance: SahamTrendingRepository? = null

        fun getInstance(apiService: ApiService): SahamTrendingRepository {
            return instance ?: synchronized(this) {
                instance ?: SahamTrendingRepository(apiService)
            }.also { instance = it }
        }
    }
}