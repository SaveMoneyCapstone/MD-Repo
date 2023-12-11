package com.dicoding.savemoney.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [UserData::class, UserIncome::class],
    version = 2,
    exportSchema = false,
)
abstract class TransactionDB: RoomDatabase() {
    abstract fun dataDao(): DataDao

    companion object {
        @Volatile
        private var INSTANCE: TransactionDB? = null

        @JvmStatic
        fun getDatabase(context: Context): TransactionDB {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    TransactionDB::class.java, "TransactionDb"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                    .also { INSTANCE = it }
            }
        }
    }
}