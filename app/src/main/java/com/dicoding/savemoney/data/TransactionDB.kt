package com.dicoding.savemoney.data

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [UserData::class],
    version = 2,
    exportSchema = false,
)
abstract class TransactionDB: RoomDatabase() {
    abstract fun
}