package com.dicoding.savemoney.ui.saham

import androidx.lifecycle.*
import com.dicoding.savemoney.data.*
import com.dicoding.savemoney.data.repository.*
import com.dicoding.savemoney.data.response.*

class SahamTrendingViewModel(repository: CompanyProfileRepository) : ViewModel() {

    val profileCompany: LiveData<ResultState<SahamTrendingResponse>> =
        repository.getSahamTrending()
}


