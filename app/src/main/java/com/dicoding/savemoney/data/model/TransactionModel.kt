package com.dicoding.savemoney.data.model

import android.os.Parcelable
import com.dicoding.savemoney.utils.*
import kotlinx.parcelize.Parcelize
import java.util.*
@Parcelize
data class TransactionModel(
    val id: String = "", var amount: Double ?= null, var category: String ?= null, var note: String ?= null, var date: Date ?= null, val transactionType: TransactionType ?= null
) : Parcelable

