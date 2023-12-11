package com.dicoding.savemoney.data

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
import java.sql.Date


@Entity
@Parcelize
data class UserData(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    @ColumnInfo(name = "icon_code")
    val code: Int,

    @ColumnInfo(name = "amount")
    val amount: Long,

    @ColumnInfo(name = "note")
    val note: String,

    @ColumnInfo(name = "category")
    val category: String,

    @ColumnInfo(name = "date")
    val date: Long,
) : Parcelable

@Entity
@Parcelize
data class UserIncome (
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    @ColumnInfo(name = "icon_code")
    val code: Int,

    @ColumnInfo(name = "amount")
    val amount: Long,

    @ColumnInfo(name = "note")
    val note: String,

    @ColumnInfo(name = "category")
    val category: String,

    @ColumnInfo(name = "date")
    val date: Long,
): Parcelable

data class Date(
    val id: Int,
    val date: String,
    val list: List<UserData>
)