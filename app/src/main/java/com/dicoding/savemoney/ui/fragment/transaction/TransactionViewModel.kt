package com.dicoding.savemoney.ui.fragment.transaction

import androidx.lifecycle.*
import com.dicoding.savemoney.data.TransactionRepository

class TransactionViewModel(private val repository: TransactionRepository): ViewModel() {
    private val _text = MutableLiveData<String>().apply {
        value = "This is transaction Fragment"
    }
    val text: LiveData<String> = _text

    fun getDataByMonth(first: Long, end: Long) = repository.getDataByMonth(first,end)

}