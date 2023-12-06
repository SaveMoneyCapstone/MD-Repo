package com.dicoding.savemoney.ui

import android.content.Context
import com.dicoding.savemoney.data.TransactionDB
import com.dicoding.savemoney.data.TransactionRepository
import com.dicoding.savemoney.ui.add_expenditure.AddExpensesFragment

object Injection {
    fun provideRepo(context: Context) : TransactionRepository {
        val db = TransactionDB.getDatabase(context)
        val dao = db.dataDao()
        return TransactionRepository(dao)
    }
}