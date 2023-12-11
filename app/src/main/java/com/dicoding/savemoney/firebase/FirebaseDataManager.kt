package com.dicoding.savemoney.firebase

import android.util.Log
import com.dicoding.savemoney.data.local.entity.UserData
import com.dicoding.savemoney.utils.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.*
import java.util.Date

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

    fun getHistory(callback: (List<Float>, List<String>, List<String>, List<Date>) -> Unit
    ) {
        val userId = firebaseAuth.currentUser?.uid
        if (userId != null) {
            // Fetch data from Firebase and call the callback
            val incomesRef = firestore.collection("users").document(userId).collection("incomes")
            val expensesRef = firestore.collection("users").document(userId).collection("expense")
            val amountData = mutableListOf<Float>()
            val categoryData = mutableListOf<String>()
            val notesData = mutableListOf<String>()
            val timestamps = mutableListOf<Date>()
            incomesRef.get().addOnSuccessListener { incomesSnapshot ->
                for (incomeDocument in incomesSnapshot) {
                    val amount = incomeDocument.getDouble("amount") ?: 0.0
                    val category = incomeDocument.getString("category")
                    val notes = incomeDocument.getString("note")
                    val timestamp = incomeDocument.getTimestamp("timestamp")
                    amountData.add(amount.toFloat())
                    categoryData.add(category.toString())
                    notesData.add(notes.toString())
                    timestamps.add(timestamp?.toDate() ?: Date())
                }

                expensesRef.get().addOnSuccessListener { expensesSnapshot ->
                    for (expenseDocument in expensesSnapshot) {
                        val amount = expenseDocument.getDouble("amount") ?: 0.0
                        val category = expenseDocument.getString("category")
                        val notes = expenseDocument.getString("note")
                        val timestamp = expenseDocument.getTimestamp("timestamp")
                        amountData.add(amount.toFloat())
                        categoryData.add(category.toString())
                        notesData.add(notes.toString())
                        timestamps.add(timestamp?.toDate() ?: Date())
                    }
                    callback.invoke(amountData,categoryData,notesData,timestamps)
                }

            }
        } else {
                Log.e("LineChartManager", "Error fetching data")
        }
    }
}
