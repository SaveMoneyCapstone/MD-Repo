package com.dicoding.savemoney.data

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize


@Entity
@Parcelize
data class UserData(
    @PrimaryKey(autoGenerate = false)
    val id: String,

    val amount: Int,
    val note: String,
    val category: Int,
    val date: Long,
) : Parcelable
