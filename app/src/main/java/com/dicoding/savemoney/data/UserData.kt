package com.dicoding.savemoney.data

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize


@Entity
@Parcelize
data class UserData(
    @PrimaryKey(autoGenerate = false)
    val id: Int = 0,

    @ColumnInfo(name = "amount")
    val amount: Long,

    @ColumnInfo(name = "note")
    val note: String,

    @ColumnInfo(name = "category")
    val category: Int,

    @ColumnInfo(name = "date")
    val date: Long,
) : Parcelable
