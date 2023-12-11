package com.dicoding.savemoney.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.RawQuery
import androidx.sqlite.db.SupportSQLiteQuery
import javax.sql.DataSource



@Dao
interface DataDao {


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertData(data: UserData)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertIncome(income: UserIncome)

    @Query("SELECT * FROM UserData UNION ALL SELECT * FROM UserIncome ORDER BY date DESC")
    fun getData(): LiveData<List<UserData>>


    @Query("SELECT sum(amount) from userdata")
    fun getExpenses(): LiveData<Long>

    @Query("SELECT sum(amount) from userincome")
    fun getIncome(): LiveData<Long>
//  @Query("SELECT * FROM UserData UNION ALL SELECT * FROM UserIncome WHERE date between :month ")
//    fun getDateByMonth(month:String) : LiveData<List<UserData>>

    @Query("SELECT * from userdata where date >= :first AND date <= :end UNION SELECT * from userincome where date >= :first AND date <= :end ORDER by date DESC ")
    fun getDataByMonth(first: Long, end: Long) : LiveData<List<UserData>>

    @Query("SELECT sum(amount) from userdata where date between :first AND :end ")
    fun getSumExpensesByDay(first: Long, end: Long) : LiveData<Long>
}

