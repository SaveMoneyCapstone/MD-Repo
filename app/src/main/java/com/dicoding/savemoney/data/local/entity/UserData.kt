package com.dicoding.savemoney.data.local.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.Date

@Parcelize
data class UserData(
    val id: String = "0",
    val amount: Long,

    val note: String,

    val category: String,

    val date: Date,
) : Parcelable