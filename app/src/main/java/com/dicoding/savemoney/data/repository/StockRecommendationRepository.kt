package com.dicoding.savemoney.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.dicoding.savemoney.data.ResultState
import com.dicoding.savemoney.data.api.ApiService
import com.dicoding.savemoney.data.response.predict.PredictResponse
import com.dicoding.savemoney.data.response.RecommendationResponse
import com.dicoding.savemoney.data.response.UserData

class StockRecommendationRepository(private val apiService: ApiService) {

    fun getStock(userData: UserData): LiveData<ResultState<RecommendationResponse>> = liveData {
        emit(ResultState.Loading)
        try {
            val response = apiService.getRecommendation(userData)
            if(response.isSuccessful) {
                val stockRecomResponse = response.body()?.data
                if (stockRecomResponse != null) {
                    val stockResponse = RecommendationResponse(stockRecomResponse)
                    emit(ResultState.Success(stockResponse))
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

    fun getPredict(userData: UserData): LiveData<ResultState<PredictResponse>> = liveData {
        emit(ResultState.Loading)
        try {
            val response = apiService.getPredict(userData)
            if(response.isSuccessful) {
                val predictRecomResponse = response.body()?.data
                if(predictRecomResponse != null) {
                    val predictResponse = PredictResponse(predictRecomResponse)
                    emit(ResultState.Success(predictResponse))
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
        private var instance: StockRecommendationRepository? = null

        fun getInstance(apiService: ApiService): StockRecommendationRepository {
            return instance ?: synchronized(this) {
                instance ?: StockRecommendationRepository(apiService)
            }.also { instance = it }
        }
    }
}