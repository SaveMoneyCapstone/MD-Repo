package com.dicoding.savemoney.ui.fragment.sahamtrending

import androidx.lifecycle.*
import com.dicoding.savemoney.data.*
import com.dicoding.savemoney.data.repository.*
import com.dicoding.savemoney.data.response.*

class SahamTrendingViewModel(repository: SahamTrendingRepository) : ViewModel() {

    val fetchSahamTrending: LiveData<ResultState<SahamTrendingResponse>> =
        repository.getSahamTrending()
}


