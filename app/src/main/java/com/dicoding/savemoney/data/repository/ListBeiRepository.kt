package com.dicoding.savemoney.data.repository

import androidx.lifecycle.*
import com.dicoding.savemoney.BuildConfig.API_KEY
import com.dicoding.savemoney.data.*
import com.dicoding.savemoney.data.api.*
import com.dicoding.savemoney.data.response.*

class ListBeiRepository(private val apiService: ApiService) {

     fun getBeiCompanies(): LiveData<ResultState<ListBeiResponse>> = liveData {
        emit(ResultState.Loading)
        try {
            val response = apiService.getBeiCompany(API_KEY)
            if (response.isSuccessful) {
                val beiCompanyResponse = response.body()?.data
                if (beiCompanyResponse != null) {
                    val beiCompany = ListBeiResponse(beiCompanyResponse)
                    emit(ResultState.Success(beiCompany))
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
        private var instance: ListBeiRepository? = null
        fun getInstance(
            apiService: ApiService,
        ): ListBeiRepository =
            instance ?: synchronized(this) {
                instance ?: ListBeiRepository(apiService)
            }.also { instance = it }
    }
}

