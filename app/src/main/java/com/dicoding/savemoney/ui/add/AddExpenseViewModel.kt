package com.dicoding.savemoney.ui.add

import androidx.lifecycle.ViewModel
import com.dicoding.savemoney.data.TransactionRepository
import com.dicoding.savemoney.data.UserData

class AddExpenseViewModel(private val repository: TransactionRepository) : ViewModel() {
    fun saveData(data: UserData) = repository.insertData(data)
}