package com.dicoding.savemoney.ui.fragment.ojk

import androidx.lifecycle.*
import com.dicoding.savemoney.data.repository.OjkInvestmentRepository
import com.dicoding.savemoney.data.ResultState
import com.dicoding.savemoney.data.response.*
import kotlinx.coroutines.launch

class OjkInvestmentViewModel(private val ojkInvestmentRepository: OjkInvestmentRepository) :
    ViewModel() {

    private val _ojkInvestmentData = MutableLiveData<ResultState<OjkResponse>>()
    val ojkInvestmentData: LiveData<ResultState<OjkResponse>>
        get() = _ojkInvestmentData

    fun fetchOjkInvestment() {
        viewModelScope.launch {
            try {
                _ojkInvestmentData.value = ResultState.Loading
                ojkInvestmentRepository.getOjkInvestment().observeForever { resultState ->
                    resultState.let {
                        _ojkInvestmentData.value = it
                    }
                }
            } catch (e: Exception) {
                _ojkInvestmentData.value = ResultState.Error(e.message.toString())
            }
        }
    }

}
