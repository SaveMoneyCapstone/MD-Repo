package com.dicoding.savemoney.ui.fragment.dashboard

import androidx.lifecycle.*
import com.dicoding.savemoney.data.*
import com.dicoding.savemoney.data.repository.*
import com.dicoding.savemoney.data.response.*
import kotlinx.coroutines.*

class CompanyProfileViewModel(private val repository: CompanyProfileRepository) : ViewModel() {

    private val _profileCompany = MutableLiveData<ResultState<ProfileCompanyResponse>>()
    val profileCompany: LiveData<ResultState<ProfileCompanyResponse>> get() = _profileCompany

    fun fetchCompanyProfile(symbol: String) {
        viewModelScope.launch {
            _profileCompany.value = ResultState.Loading
            _profileCompany.value = repository.getCompanyProfile(symbol)
        }
    }


}