package com.dicoding.savemoney.ui.login

import androidx.lifecycle.ViewModel
import com.dicoding.savemoney.data.*
import com.dicoding.savemoney.data.preference.*
import com.dicoding.savemoney.data.repository.*

class LoginViewModel(private val repository: AuthRepository): ViewModel() {

    suspend fun login(email: String, password: String): ResultState<AuthModel> {
        return repository.login(email, password)
    }

    suspend fun saveSession(userModel: AuthModel) {
        repository.saveSession(userModel)
    }
}