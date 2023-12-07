package com.dicoding.savemoney.ui.fragment.dashboard

import androidx.lifecycle.*
import com.dicoding.savemoney.data.*
import com.dicoding.savemoney.data.repository.*
import com.dicoding.savemoney.data.response.*
import kotlinx.coroutines.*

class CompanyProfileViewModel(private val repository: CompanyProfileRepository) : ViewModel() {
    private val _pprofileCompany = MutableLiveData<ResultState<CompanyProfileResponse>>()

    val profileCompany: LiveData<ResultState<CompanyProfileResponse>> get() = _pprofileCompany

    fun fetchCompanyProfile() {
        viewModelScope.launch {
            try {

                _pprofileCompany.value = ResultState.Loading
                repository.getCompanyProfile().observeForever { result ->
                    result.let {
                        _pprofileCompany.value = it
                    }
                }
            } catch (e: Exception) {
                _pprofileCompany.value = ResultState.Error(e.message.toString())
            }

        }
    }
}
