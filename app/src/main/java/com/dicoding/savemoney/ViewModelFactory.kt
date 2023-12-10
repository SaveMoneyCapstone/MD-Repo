package com.dicoding.savemoney

import android.content.*
import androidx.lifecycle.*
import com.dicoding.savemoney.data.repository.*
import com.dicoding.savemoney.di.*
import com.dicoding.savemoney.ui.fragment.dashboard.*
import com.dicoding.savemoney.ui.fragment.ojk.*
import com.dicoding.savemoney.ui.fragment.sahamtrending.*
import com.dicoding.savemoney.ui.login.*
import com.dicoding.savemoney.ui.main.*
import com.dicoding.savemoney.ui.signup.*
import com.google.android.play.integrity.internal.*

@Suppress("UNCHECKED_CAST")
class ViewModelFactory private constructor(
    private val sahamTrendingRepository: SahamTrendingRepository,
    private val ojkInvestmentRepository: OjkInvestmentRepository,
) :
    ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {

        if (modelClass.isAssignableFrom(OjkInvestmentViewModel::class.java)) {
            return OjkInvestmentViewModel(ojkInvestmentRepository) as T
        } else if (modelClass.isAssignableFrom(SahamTrendingViewModel::class.java)) {
            return SahamTrendingViewModel(sahamTrendingRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }

    companion object {
        @Volatile
        private var instance: ViewModelFactory? = null
        fun getInstance(): ViewModelFactory =
            instance ?: synchronized(this) {
                instance ?: ViewModelFactory(
                    Injection.provideProfileCompanyRepository(),
                    Injection.provideOjkInvestmentRepository(),
                )
            }.also { instance = it }
    }
}