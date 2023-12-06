package com.dicoding.savemoney.ui

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dicoding.savemoney.data.TransactionRepository
import com.dicoding.savemoney.ui.add.AddExpenseViewModel
import com.dicoding.savemoney.ui.add_expenditure.AddExpensesFragment

class ViewModelFactory private constructor(private val transactionRepo: TransactionRepository) : ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AddExpenseViewModel::class.java)) {
            return AddExpenseViewModel(transactionRepo) as T
        }
    throw IllegalArgumentException("Unknown ViewModel class : " + modelClass.name)
    }

    companion object {
        @Volatile
        private var instance: ViewModelFactory? = null
        fun getInstance(context: Context): ViewModelFactory {
            if (instance == null) {
                synchronized(ViewModelFactory::class.java) {
                    instance = ViewModelFactory(Injection.provideRepo(context))
                }
            }
            return instance as ViewModelFactory
        }
    }
}