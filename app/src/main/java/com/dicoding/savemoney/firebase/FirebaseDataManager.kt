package com.dicoding.savemoney.firebase

import android.util.Log
import com.dicoding.savemoney.data.Transaction
import com.dicoding.savemoney.data.model.TransactionModel
import com.dicoding.savemoney.utils.*
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.*
import com.google.firebase.firestore.AggregateField.average
import com.google.firestore.v1.StructuredAggregationQuery.Aggregation.AVG_FIELD_NUMBER
import com.google.firestore.v1.StructuredAggregationQuery.Aggregation.Avg
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

class FirebaseDataManager {

    private val firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()
    private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()

    fun getCurrentUserName(userId: String, onSuccess: (String) -> Unit) {
        firestore.collection("users").document(userId)
            .get()
            .addOnSuccessListener { document ->
                if (document != null) {
                    val name = document.getString("name")
                    onSuccess.invoke(name.orEmpty())
                }
            }
    }

    fun calculateBalance(callback: (String) -> Unit) {
        val userId = firebaseAuth.currentUser?.uid
        if (userId != null) {
            val expensesRef = firestore.collection("users").document(userId).collection("expense")
            val incomesRef = firestore.collection("users").document(userId).collection("incomes")

            // Fetch incomes
            incomesRef.get().addOnSuccessListener { incomesSnapshot ->
                var totalIncome = 0.0
                for (incomeDocument in incomesSnapshot) {
                    val amount = incomeDocument.getDouble("amount") ?: 0.0
                    totalIncome += amount
                }

                // Fetch expenses
                expensesRef.get().addOnSuccessListener { expensesSnapshot ->
                    var totalExpense = 0.0
                    for (expenseDocument in expensesSnapshot) {
                        val amount = expenseDocument.getDouble("amount") ?: 0.0
                        totalExpense += amount
                    }

                    // Calculate balance
                    val balance = totalIncome - totalExpense

                    val formattedBalance = formatCurrencyTransaction(balance)
                    callback.invoke(formattedBalance)
                }
            }.addOnFailureListener {
                callback.invoke(formatCurrencyTransaction(0.0))
            }
        } else {
            callback.invoke(formatCurrencyTransaction(0.0))
        }
    }

    fun getIncomes(callback: (String) -> Unit) {
        val userId = firebaseAuth.currentUser?.uid
        if (userId != null) {
            firestore.collection("users").document(userId).collection("incomes")
                .get()
                .addOnSuccessListener { querySnapshot ->
                    var balance = 0.0
                    for (document in querySnapshot) {
                        val amount = document.getDouble("amount") ?: 0.0
                        balance += amount
                    }
                    val formattedIncomes = formatCurrencyTransaction(balance)
                    callback.invoke(formattedIncomes)
                }
                .addOnFailureListener {
                    callback.invoke(formatCurrencyTransaction(0.0))
                }
        } else {
            callback.invoke(formatCurrencyTransaction(0.0))
        }
    }

    fun getExpense(callback: (String) -> Unit) {
        val userId = firebaseAuth.currentUser?.uid
        if (userId != null) {
            firestore.collection("users").document(userId).collection("expense")
                .get()
                .addOnSuccessListener { querySnapshot ->
                    var balance = 0.0
                    for (document in querySnapshot) {
                        val amount = document.getDouble("amount") ?: 0.0
                        balance += amount
                    }
                    val formattedExpense = formatCurrencyTransaction(balance)
                    callback.invoke(formattedExpense)
                }
                .addOnFailureListener {
                    callback.invoke(formatCurrencyTransaction(0.0))
                }
        } else {
            callback.invoke(formatCurrencyTransaction(0.0))
        }
    }

    fun getHistory(
        callback: (MutableList<TransactionModel>) -> Unit
    ) {
        val userId = firebaseAuth.currentUser?.uid
        if (userId != null) {
            // Fetch data from Firebase and call the callback
            val incomesRef = firestore.collection("users").document(userId).collection("incomes").orderBy("date",Query.Direction.DESCENDING)
            val expensesRef = firestore.collection("users").document(userId).collection("expense").orderBy("date", Query.Direction.DESCENDING)
            var incomeList: ArrayList<TransactionModel>
            incomeList = arrayListOf()
            var expenseList: ArrayList<TransactionModel>
            expenseList = arrayListOf()
            incomesRef.get().addOnSuccessListener { incomesSnapshot ->
                expensesRef.get().addOnSuccessListener { expensesSnapshot ->
                for (document in incomesSnapshot) {
                    val id = document.id
                    val amount = document.getDouble("amount") ?: 0.0
                    val category = document.getString("category") ?: ""
                    val note = document.getString("note") ?: ""
                    val date = document.getTimestamp("date")?.toDate()
                    val transaction = TransactionModel(
                        id,
                        amount,
                        category,
                        note,
                        date,
                        TransactionType.INCOME
                    )
                    incomeList.add(transaction)
                }
                    for (document in expensesSnapshot) {
                    val id = document.id
                    val amount = document.getDouble("amount") ?: 0.0
                    val category = document.getString("category") ?: ""
                    val note = document.getString("note") ?: ""
                    val date = document.getTimestamp("date")?.toDate()
                    val transaction = TransactionModel(
                        id,
                        amount,
                        category,
                        note,
                        date,
                        TransactionType.EXPENSE
                    )
                    expenseList.add(transaction)
                }
                    val combinedList = mutableListOf<TransactionModel>()
                    combinedList.addAll(incomeList)
                    combinedList.addAll(expenseList)
                    combinedList.sortByDescending { it.date }


                    callback.invoke(combinedList)
                }

            }
                .addOnFailureListener {e ->
                    Log.e("FirebaseTransactionManager", "Error getting transactions", e)
                }
        } else {
        }
    }


    fun fetchData(
        callback: (List<Int>, List<Int>) -> Unit
    ) {
        // Fetch data from Firebase and call the callback
        val userId = firebaseAuth.currentUser?.uid
        if (userId != null) {
            val incomesRef = firestore.collection("users").document(userId).collection("incomes")
                .orderBy("date", Query.Direction.DESCENDING).limit(7)
            val expensesRef = firestore.collection("users").document(userId).collection("expense")
                .orderBy("date", Query.Direction.DESCENDING).limit(7)

            val dataIncome = mutableListOf<Int>()
            val dataExpense = mutableListOf<Int>()

            incomesRef.get().addOnSuccessListener { incomesSnapshot ->
                for (incomeDocument in incomesSnapshot) {
                    val amount = incomeDocument.getDouble("amount") ?: 0.0
                    dataIncome.add(amount.toInt())
                }

                expensesRef.get().addOnSuccessListener { expensesSnapshot ->
                    for (expenseDocument in expensesSnapshot) {
                        val amount = expenseDocument.getDouble("amount") ?: 0.0
                        dataExpense.add(amount.toInt())
                    }

                    callback.invoke(dataIncome, dataExpense)
                }
            }.addOnFailureListener { exception ->
                // Handle failure
                Log.e("LineChartManager", "Error fetching data: ${exception.message}")
            }
        }
    }

    fun getHistoryMonth(callback: (MutableList<TransactionModel>, Double, Double, Double) -> Unit
    ) {
        val userId = firebaseAuth.currentUser?.uid
        if (userId != null) {
            val calendar = Calendar.getInstance()
            val month = calendar.get(Calendar.MONTH)+1
            val year = calendar.get(Calendar.YEAR)
            val first= "$year-$month-01"
            val date: Date? = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse(first)
            val firstDateMillis: Long = date!!.time/1000
            val end = "$year-$month-31"
            val endDate: Date? = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse(end)
            val endDateMillis: Long = endDate!!.time/1000
            // Fetch data from Firebase and call the callback
            val incomesRef = firestore.collection("users").document(userId)
                .collection("incomes")
                .whereGreaterThanOrEqualTo("date", Timestamp(firstDateMillis, 0))
                .whereLessThanOrEqualTo("date", Timestamp(endDateMillis, 0))
                .orderBy("date", Query.Direction.DESCENDING)
            val expensesRef = firestore.collection("users").document(userId)
                .collection("expense").whereGreaterThanOrEqualTo("date", Timestamp(firstDateMillis, 0))
                .whereLessThanOrEqualTo("date", Timestamp(endDateMillis, 0))
                .orderBy("date", Query.Direction.DESCENDING)
            val expensesCount = expensesRef.count()
            var incomeList: ArrayList<TransactionModel>
            incomeList = arrayListOf()
            var expenseList: ArrayList<TransactionModel>
            expenseList = arrayListOf()
            var balanceIncome = 0.0
            var balanceExpense = 0.0
            var expenseAverage = 0.0
            incomesRef.get().addOnSuccessListener { incomesSnapshot ->
                expensesRef.get().addOnSuccessListener { expensesSnapshot ->
                    expensesCount.get(AggregateSource.SERVER).addOnCompleteListener {task ->
                        for (document in incomesSnapshot) {
                            val id = document.id
                            val amount = document.getDouble("amount") ?: 0.0
                            val category = document.getString("category") ?: ""
                            val note = document.getString("note") ?: ""
                            val date = document.getTimestamp("date")?.toDate()
                            val transaction = TransactionModel(
                                id,
                                amount,
                                category,
                                note,
                                date,
                                TransactionType.INCOME
                            )
                            balanceIncome += amount
                            incomeList.add(transaction)
                        }
                        for (document in expensesSnapshot) {
                            val id = document.id
                            val amount = document.getDouble("amount") ?: 0.0
                            val category = document.getString("category") ?: ""
                            val note = document.getString("note") ?: ""
                            val date = document.getTimestamp("date")?.toDate()
                            val transaction = TransactionModel(
                                id,
                                amount,
                                category,
                                note,
                                date,
                                TransactionType.EXPENSE
                            )
                            val snapshot = task.result
                            balanceExpense += amount
                            expenseList.add(transaction)
                            expenseAverage = balanceExpense/snapshot.count
                        }
                        val combinedList = mutableListOf<TransactionModel>()
                        combinedList.addAll(incomeList)
                        combinedList.addAll(expenseList)
                        combinedList.sortByDescending { it.date }


                        callback.invoke(
                            combinedList,
                            balanceIncome,
                            balanceExpense,
                            expenseAverage
                        )
                    }

                }
                    .addOnFailureListener { e ->
                        Log.e("FirebaseTransactionManager", "Error getting transactions", e)
                    }
            }
        }
    }

    fun getLastMonth(callback: (MutableList<TransactionModel>, Double, Double, Double) -> Unit
    ) {
        val userId = firebaseAuth.currentUser?.uid
        if (userId != null) {
            val calendar = Calendar.getInstance()
            val month = calendar.get(Calendar.MONTH)
            val year = calendar.get(Calendar.YEAR)
            val first= "$year-$month-01"
            val date: Date? = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse(first)
            val firstDateMillis: Long = date!!.time/1000
            val end = "$year-$month-31"
            val endDate: Date? = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse(end)
            val endDateMillis: Long = endDate!!.time/1000
            // Fetch data from Firebase and call the callback
            val incomesRef = firestore.collection("users").document(userId)
                .collection("incomes")
                .whereGreaterThanOrEqualTo("date", Timestamp(firstDateMillis, 0))
                .whereLessThanOrEqualTo("date", Timestamp(endDateMillis, 0))
                .orderBy("date", Query.Direction.DESCENDING)
            val expensesRef = firestore.collection("users").document(userId)
                .collection("expense").whereGreaterThanOrEqualTo("date", Timestamp(firstDateMillis, 0))
                .whereLessThanOrEqualTo("date", Timestamp(endDateMillis, 0))
                .orderBy("date", Query.Direction.DESCENDING)
            val expensesCount = expensesRef.count()
            var incomeList: ArrayList<TransactionModel> = arrayListOf()
            var expenseList: ArrayList<TransactionModel> = arrayListOf()
            var balanceIncome = 0.0
            var balanceExpense = 0.0
            var expenseAverage = 0.0
            incomesRef.get().addOnSuccessListener { incomesSnapshot ->
                expensesRef.get().addOnSuccessListener { expensesSnapshot ->
                    expensesCount.get(AggregateSource.SERVER).addOnCompleteListener {task ->
                        for (document in incomesSnapshot) {
                            val id = document.id
                            val amount = document.getDouble("amount") ?: 0.0
                            val category = document.getString("category") ?: ""
                            val note = document.getString("note") ?: ""
                            val date = document.getTimestamp("date")?.toDate()
                            val transaction = TransactionModel(
                                id,
                                amount,
                                category,
                                note,
                                date,
                                TransactionType.INCOME
                            )
                            balanceIncome += amount
                            incomeList.add(transaction)
                        }
                        for (document in expensesSnapshot) {
                            val id = document.id
                            val amount = document.getDouble("amount") ?: 0.0
                            val category = document.getString("category") ?: ""
                            val note = document.getString("note") ?: ""
                            val date = document.getTimestamp("date")?.toDate()
                            val transaction = TransactionModel(
                                id,
                                amount,
                                category,
                                note,
                                date,
                                TransactionType.EXPENSE
                            )
                            val snapshot = task.result
                            balanceExpense += amount
                            expenseList.add(transaction)
                            expenseAverage = balanceExpense/snapshot.count
                        }
                        val combinedList = mutableListOf<TransactionModel>()
                        combinedList.addAll(incomeList)
                        combinedList.addAll(expenseList)
                        combinedList.sortByDescending { it.date }


                        callback.invoke(
                            combinedList,
                            balanceIncome,
                            balanceExpense,
                            expenseAverage
                        )
                    }

                }
                    .addOnFailureListener { e ->
                        Log.e("FirebaseTransactionManager", "Error getting transactions", e)
                    }
            }
        }
    }

    fun getExpenseForToday(callback: (Int) -> Unit) {
        val userId = firebaseAuth.currentUser?.uid
        if (userId != null) {
            val calendar = Calendar.getInstance()
            val day = calendar.get(Calendar.DAY_OF_MONTH)
            val tomorrow = calendar.get(Calendar.DAY_OF_MONTH)+1
            val month = calendar.get(Calendar.MONTH)+1
            val year = calendar.get(Calendar.YEAR)
            val first= "$year-$month-$day"
            val date: Date? = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse(first)
            val firstDate: Long = date!!.time/1000
            val end = "$year-$month-$tomorrow"
            val date2: Date? = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse(end)
            val secondDate: Long = date2!!.time/1000

            firestore.collection("users").document(userId).collection("expense").whereGreaterThanOrEqualTo("date", Timestamp(firstDate,0))
                .whereLessThanOrEqualTo("date", Timestamp(secondDate,0))
                .get()
                .addOnSuccessListener { querySnapshot ->
                    var balance = 0.0
                    for (document in querySnapshot) {
                        val amount = document.getDouble("amount") ?: 0.0
                        balance += amount
                    }
                    val formattedExpense = balance
                    callback.invoke(formattedExpense.toInt())
                }
                .addOnFailureListener {
                    callback.invoke(0)
                }
        } else {
            callback.invoke(0)
        }
    }
}
