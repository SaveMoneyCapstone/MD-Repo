package com.dicoding.savemoney.ui.fragment.dashboard

import androidx.lifecycle.*

class DashboardViewModel: ViewModel() {
    private val _text = MutableLiveData<String>().apply {
        value = "This is dashboard Fragment"
    }
    val text: LiveData<String> = _text

}