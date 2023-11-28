package com.dicoding.savemoney.ui.fragment.other

import androidx.lifecycle.*

class OtherViewModel: ViewModel() {
    private val _text = MutableLiveData<String>().apply {
        value = "This is other Fragment"
    }
    val text: LiveData<String> = _text
}