package com.dicoding.savemoney.data.repository

import com.dicoding.savemoney.data.*
import com.dicoding.savemoney.data.api.*
import com.dicoding.savemoney.data.preference.*


class AuthRepository private constructor(
    private val apiService: ApiService,
    private val userPreference: AuthPreference
) {
    suspend fun register(name: String, email: String, password: String): ResultState<Boolean> {
        return try {
            apiService.register(name, email, password).message
            ResultState.Success(true)
        } catch (e: Exception) {
            ResultState.Error(e.message ?: "Terjadi Kesalahan")
        }
    }

    suspend fun logout() = userPreference.logout()

    suspend fun login(email: String, password: String): ResultState<AuthModel> {
        return try {
            val loginResponse = apiService.login(email, password)
            if (loginResponse.loginResult != null) {
                val userModel = AuthModel(
                    email = loginResponse.loginResult.name ?: "",
                    token = loginResponse.loginResult.token ?: "",
                    isLogin = true
                )
                userPreference.saveSession(userModel)
                ResultState.Success(userModel)
            } else {
                ResultState.Error("Login Gagal")
            }
        } catch (e: Exception) {
            return ResultState.Error(e.message ?: "Terjadi Kesalahan")
        }
    }

    suspend fun saveSession(userModel: AuthModel) {
        userPreference.saveSession(userModel)
    }


    companion object {
        @Volatile
        private var instance: AuthRepository? = null

        fun getInstanceAuth(
            apiService: ApiService,
            userPreference: AuthPreference
        ): AuthRepository =
            instance ?: synchronized(this) {
                instance ?: AuthRepository(apiService, userPreference)
            }.also { instance = it }
    }
}