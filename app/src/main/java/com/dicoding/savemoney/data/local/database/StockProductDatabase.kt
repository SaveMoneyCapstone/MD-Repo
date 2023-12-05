package com.dicoding.savemoney.data.local.database

import android.content.*
import androidx.room.*
import androidx.sqlite.db.*
import com.dicoding.savemoney.data.local.entity.*

@Database(entities = [StockProductEntity::class], version = 1, exportSchema = false)
abstract class StockProductDatabase : RoomDatabase() {

    abstract fun stockProductDao(): StockProductDao

    companion object {
        @Volatile
        private var instance: StockProductDatabase? = null

        fun getInstance(context: Context): StockProductDatabase =
            instance ?: synchronized(this) {
                instance ?: Room.databaseBuilder(
                    context.applicationContext,
                    StockProductDatabase::class.java, "stockProduct"
                )
                    .addCallback(object : RoomDatabase.Callback() {
                        override fun onCreate(db: SupportSQLiteDatabase) {
                            super.onCreate(db)
                        }
                    })
                    .build()
            }
    }
}
