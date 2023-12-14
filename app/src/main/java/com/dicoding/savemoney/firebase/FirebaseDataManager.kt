package com.dicoding.savemoney.firebase

import android.util.Log
import com.dicoding.savemoney.data.Transaction
import com.dicoding.savemoney.data.model.TransactionModel
import com.dicoding.savemoney.utils.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.*

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

    fun getHistoryMonth(
        callback: (List<Transaction>) -> Unit
    ) {
        val userId = firebaseAuth.currentUser?.uid
        if (userId != null) {
            // Fetch data from Firebase and call the callback
            val incomesRef = firestore.collection("users").document(userId).collection("incomes").orderBy("date",Query.Direction.DESCENDING)
            val expensesRef = firestore.collection("users").document(userId).collection("expense").orderBy("date",Query.Direction.DESCENDING)
            var userList: ArrayList<Transaction>
            userList = arrayListOf()
            incomesRef.get().addOnSuccessListener { incomesSnapshot ->

                for (incomeDocument in incomesSnapshot) {
                    userList.add(incomeDocument.toObject(Transaction::class.java))
                }

                expensesRef.get().addOnSuccessListener { expensesSnapshot ->
                    for (expenseDocument in expensesSnapshot) {
                        userList.add(expenseDocument.toObject(Transaction::class.java))

                    }
                    callback.invoke(userList)
                }

            }
        } else {
            Log.e("LineChartManager", "Error fetching data")
        }

    }
}
