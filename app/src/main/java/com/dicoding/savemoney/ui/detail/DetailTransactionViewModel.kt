package com.dicoding.savemoney.ui.detail

import androidx.lifecycle.*
import com.dicoding.savemoney.data.model.*

class DetailTransactionViewModel: ViewModel() {

    private val _transactionData = MutableLiveData<TransactionModel>()
    val transactionData: LiveData<TransactionModel> get() = _transactionData

    fun updateTransactionData(updatedTransaction: TransactionModel) {
        _transactionData.value = updatedTransaction
    }
}