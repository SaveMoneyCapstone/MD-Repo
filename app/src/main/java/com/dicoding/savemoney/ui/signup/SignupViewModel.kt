package com.dicoding.savemoney.ui.signup

import androidx.lifecycle.ViewModel
import com.dicoding.savemoney.data.*
import com.dicoding.savemoney.data.repository.*
import kotlinx.coroutines.*

class SignupViewModel(private val repository: AuthRepository): ViewModel() {

    suspend fun register(name: String, email: String, password: String): ResultState<Boolean> {
        return withContext(Dispatchers.IO) {
            repository.register(name, email, password)
        }
    }
}