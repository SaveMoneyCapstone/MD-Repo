package com.dicoding.savemoney.ui.fragment.statistic

import androidx.lifecycle.*
import com.dicoding.savemoney.data.TransactionRepository

class StatisticViewModel(private val repo: TransactionRepository): ViewModel() {
    private val _text = MutableLiveData<String>().apply {
        value = "This is statistic Fragment"
    }
    val text: LiveData<String> = _text

//    fun getExpensesByDay(first: Int) = repo.getDataByDay(first)
}