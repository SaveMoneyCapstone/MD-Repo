package com.dicoding.savemoney.firebase

import android.util.*
import com.dicoding.savemoney.data.model.*
import com.dicoding.savemoney.utils.*
import com.google.firebase.firestore.*

class FirebaseTransactionManager(private val userId: String) {
    private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()

    fun loadExpenseTransactions(callback: (List<TransactionModel>) -> Unit) {
        val transactionsRef = firestore
            .collection("users")
            .document(userId)
            .collection("expense")

        transactionsRef
            .orderBy("timestamp", Query.Direction.DESCENDING)
            .get()
            .addOnSuccessListener { querySnapshot ->
                val transactionList = mutableListOf<TransactionModel>()
                for (document in querySnapshot.documents) {
                    val id = document.id
                    val amount = document.getDouble("amount") ?: 0.0
                    val category = document.getString("category") ?: ""
                    val note = document.getString("note") ?: ""
                    val timestamp = document.getTimestamp("timestamp")?.toDate()

                    // Add transaction data to the list
                    val transaction = TransactionModel(
                        id,
                        amount,
                        category,
                        note,
                        timestamp,
                        TransactionType.EXPENSE
                    )
                    transactionList.add(transaction)
                }
                callback.invoke(transactionList)
            }
            .addOnFailureListener { e ->
                Log.e("FirebaseTransactionManager", "Error getting expense transactions", e)
            }
    }

    fun loadIncomeTransactions(callback: (List<TransactionModel>) -> Unit) {
        val transactionsRef = firestore
            .collection("users")
            .document(userId)
            .collection("incomes")

        transactionsRef
            .orderBy("timestamp", Query.Direction.DESCENDING)
            .get()
            .addOnSuccessListener { querySnapshot ->
                val transactionList = mutableListOf<TransactionModel>()
                for (document in querySnapshot.documents) {
                    val id = document.id
                    val amount = document.getDouble("amount") ?: 0.0
                    val category = document.getString("category") ?: ""
                    val note = document.getString("note") ?: ""
                    val timestamp = document.getTimestamp("timestamp")?.toDate()

                    // Add transaction data to the list
                    val transaction = TransactionModel(
                        id,
                        amount,
                        category,
                        note,
                        timestamp,
                        TransactionType.INCOME
                    )
                    transactionList.add(transaction)
                }
                callback.invoke(transactionList)
            }
            .addOnFailureListener { e ->
                Log.e("FirebaseTransactionManager", "Error getting income transactions", e)
            }
    }

    fun deleteTransactionIncome(transactionId: String, onComplete: (Boolean) -> Unit) {
        val transactionsCollection = firestore.collection("incomes")
        transactionsCollection.document(transactionId)
            .delete()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    onComplete(true)
                } else {
                    onComplete(false)
                }
            }
    }

    fun deleteTransactionExpense(transactionId: String, onComplete: (Boolean) -> Unit) {
        val transactionsCollection = firestore.collection("expense")
        transactionsCollection.document(transactionId)
            .delete()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    onComplete(true)
                } else {
                    onComplete(false)
                }
            }
    }
}
