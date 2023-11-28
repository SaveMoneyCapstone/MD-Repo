package com.dicoding.savemoney.ui.fragment.statistic

import androidx.lifecycle.*

class StatisticViewModel: ViewModel() {
    private val _text = MutableLiveData<String>().apply {
        value = "This is statistic Fragment"
    }
    val text: LiveData<String> = _text
}