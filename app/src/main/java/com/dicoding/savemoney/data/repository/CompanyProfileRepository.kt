package com.dicoding.savemoney.data.repository

import androidx.lifecycle.*
import com.dicoding.savemoney.BuildConfig.API_KEY
import com.dicoding.savemoney.data.*
import com.dicoding.savemoney.data.api.*
import com.dicoding.savemoney.data.response.*

class CompanyProfileRepository(private val apiService: ApiService) {

    fun getCompanyProfile(symbol: String): LiveData<ResultState<ProfileCompanyResponse>> =
        liveData {
            emit(ResultState.Loading)
            try {
                val response = apiService.getProfileCompany(API_KEY, symbol)
                if (response.isSuccessful) {
                    val profileCompanyResponse = response.body()?.data
                    if (profileCompanyResponse != null) {
                        val companyResponse = ProfileCompanyResponse(profileCompanyResponse)
                        emit(ResultState.Success(companyResponse))

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
        private var instance: CompanyProfileRepository? = null

        fun getInstance(apiService: ApiService): CompanyProfileRepository {
            return instance ?: synchronized(this) {
                instance ?: CompanyProfileRepository(apiService)
            }.also { instance = it }
        }
    }
}