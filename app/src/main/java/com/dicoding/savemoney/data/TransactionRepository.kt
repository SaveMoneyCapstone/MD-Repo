package com.dicoding.savemoney.data

import android.content.Context
import androidx.lifecycle.LiveData
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class TransactionRepository(private val dataDao: DataDao) {
    private val executorService:ExecutorService = Executors.newSingleThreadExecutor()
    fun insertData(newData: UserData) = executorService.execute {
        dataDao.insertData(newData)
    }

    fun insertIncome(income: UserIncome) = executorService.execute {
        dataDao.insertIncome(income)
    }

    fun getData() : LiveData<List<UserData>> {
        return dataDao.getData()
    }

    fun getExpenses(): LiveData<Long> {
        return dataDao.getExpenses()
    }

    fun getIncome(): LiveData<Long> {
        return dataDao.getIncome()
    }

    fun getDataByMonth(first: Long, end: Long): LiveData<List<UserData>> {
        return dataDao.getDataByMonth(first,end)
    }

//    fun getDataByDay(first: Int): LiveData<Long>{
//        return dataDao.getSumExpensesByDay(first)
//    }

}