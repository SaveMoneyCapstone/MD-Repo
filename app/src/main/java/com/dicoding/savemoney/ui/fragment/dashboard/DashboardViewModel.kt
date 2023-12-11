package com.dicoding.savemoney.ui.fragment.dashboard

import androidx.lifecycle.*
import com.dicoding.savemoney.data.TransactionRepository

class DashboardViewModel(private val repo: TransactionRepository): ViewModel() {
    private val _text = MutableLiveData<String>().apply {
        value = "This is dashboard Fragment"
    }
    val text: LiveData<String> = _text

    fun getData() = repo.getData()

    fun getExpenses() = repo.getExpenses()

    fun getIncome() = repo.getIncome()
}