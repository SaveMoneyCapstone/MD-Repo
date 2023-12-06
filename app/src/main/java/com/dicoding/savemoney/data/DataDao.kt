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

}