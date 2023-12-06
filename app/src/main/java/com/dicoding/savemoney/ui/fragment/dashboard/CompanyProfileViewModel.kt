package com.dicoding.savemoney.ui.fragment.dashboard

import androidx.lifecycle.*
import com.dicoding.savemoney.data.*
import com.dicoding.savemoney.data.repository.*
import com.dicoding.savemoney.data.response.*

class CompanyProfileViewModel(repository: CompanyProfileRepository) : ViewModel() {

    val profileCompany: LiveData<ResultState<ProfileCompanyResponse>> =
        repository.getCompanyProfile("BBCA")
}
