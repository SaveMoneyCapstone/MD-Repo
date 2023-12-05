package com.dicoding.savemoney.data.repository

import com.dicoding.savemoney.BuildConfig
import com.dicoding.savemoney.data.*
import com.dicoding.savemoney.data.api.*
import com.dicoding.savemoney.data.response.*

class CompanyProfileRepository(private val apiService: ApiService) {

    suspend fun getCompanyProfile( symbol: String): ResultState<ProfileCompanyResponse> {
        return try {
            val response = apiService.getProfileCompany(BuildConfig.API_KEY, symbol)

            if (response.isSuccessful) {
                val profileCompanyResponse = response.body()
                if (profileCompanyResponse != null) {
                    ResultState.Success(profileCompanyResponse)
                } else {
                    ResultState.Error("Response body is null")
                }
            } else {
                ResultState.Error("Error: ${response.code()}")
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