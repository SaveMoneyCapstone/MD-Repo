package com.dicoding.savemoney.ui.fragment.listbei

import androidx.lifecycle.*
import com.dicoding.savemoney.data.*
import com.dicoding.savemoney.data.repository.*
import com.dicoding.savemoney.data.response.*

class ListBeiViewModel(repository: ListBeiRepository) : ViewModel() {

    val beiCompanies: LiveData<ResultState<ListBeiResponse>> = repository.getBeiCompanies()
}
