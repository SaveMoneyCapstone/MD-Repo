package com.dicoding.savemoney.data

import android.content.Context
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class TransactionRepository(private val dataDao: DataDao) {
    private val executorService:ExecutorService = Executors.newSingleThreadExecutor()
    fun insertData(newData: UserData) = executorService.execute {
        dataDao.insertData(newData)
    }
}