package com.dicoding.savemoney

import android.content.*
import androidx.lifecycle.*
import com.dicoding.savemoney.data.repository.*
import com.dicoding.savemoney.di.*
import com.dicoding.savemoney.ui.fragment.dashboard.*
import com.dicoding.savemoney.ui.fragment.listbei.*

@Suppress("UNCHECKED_CAST")
class ViewModelFactory private constructor(
    private val listBeiRepository: ListBeiRepository,
    private val companyProfileRepository: CompanyProfileRepository
) :
    ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ListBeiViewModel::class.java)) {
            return ListBeiViewModel(listBeiRepository) as T
        } else if (modelClass.isAssignableFrom(CompanyProfileViewModel::class.java)) {
            return CompanyProfileViewModel(companyProfileRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }

    companion object {
        @Volatile
        private var instance: ViewModelFactory? = null
        fun getInstance(context: Context): ViewModelFactory =
            instance ?: synchronized(this) {
                instance ?: ViewModelFactory(
                    Injection.provideRepository(context),
                    Injection.provideProfileCompanyRepository(context)
                )
            }.also { instance = it }
    }
}