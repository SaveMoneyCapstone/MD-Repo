package com.dicoding.savemoney.data.local.entity

import androidx.room.*

@Entity(tableName = "stockProduct")
data class StockProductEntity(

    @field:PrimaryKey
    @field:ColumnInfo(name = "symbol")
    val symbol: String,

    @field:ColumnInfo(name = "name")
    val name: String,

    @field:ColumnInfo(name = "urlToImage")
    val logo: String? = null,

    @ColumnInfo(name = "isBookmarked")
    var isBookmarked: Boolean = false
)