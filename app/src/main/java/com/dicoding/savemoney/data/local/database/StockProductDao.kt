package com.dicoding.savemoney.data.local.database

import androidx.lifecycle.*
import androidx.room.*
import com.dicoding.savemoney.data.local.entity.*

@Dao
interface StockProductDao {

    @Query("SELECT * FROM stockProduct WHERE isBookmarked = 1")
    fun getBookmarkedStockProduct(): LiveData<List<StockProductEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveStockProduct(stockProduct: StockProductEntity)

    @Query("DELETE FROM stockProduct WHERE symbol = :stockSymbol")
    suspend fun deleteStockProduct(stockSymbol: String)

    @Query("UPDATE stockProduct SET isBookmarked = :isBookmarked WHERE symbol = :stockSymbol")
    suspend fun updateBookmarkStatus(stockSymbol: String, isBookmarked: Boolean)

    @Query("SELECT isBookmarked FROM stockProduct WHERE symbol = :stockSymbol")
    fun isStockProductBookmarked(stockSymbol: String): LiveData<Boolean>
}
