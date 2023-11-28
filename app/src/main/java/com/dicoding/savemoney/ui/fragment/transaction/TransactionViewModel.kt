package com.dicoding.savemoney.ui.fragment.transaction

import androidx.lifecycle.*

class TransactionViewModel: ViewModel() {
    private val _text = MutableLiveData<String>().apply {
        value = "This is transaction Fragment"
    }
    val text: LiveData<String> = _text
}