package com.dicoding.savemoney.firebase

import android.util.Log
import com.dicoding.savemoney.data.model.TransactionModel
import com.dicoding.savemoney.utils.TransactionType
import com.google.firebase.firestore.*

class FirebaseTransactionManager(private val userId: String) {
    private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()

    companion object {
        const val COLLECTION_EXPENSE = "expense"
        const val COLLECTION_INCOMES = "incomes"
    }

    private inline fun <reified T> mapTransaction(document: DocumentSnapshot): T? {
        if (document.exists()) {
            return document.toObject(T::class.java)
        }
        return null
    }

    private fun loadTransactions(collectionName: String, callback: (List<TransactionModel>) -> Unit) {
        val transactionsRef = firestore
            .collection("users")
            .document(userId)
            .collection(collectionName)

        transactionsRef
            .get()
            .addOnSuccessListener { querySnapshot ->
                val transactionList = mutableListOf<TransactionModel>()
                for (document in querySnapshot.documents) {
                    val id = document.id
                    val amount = document.getDouble("amount") ?: 0.0
                    val category = document.getString("category") ?: ""
                    val note = document.getString("note") ?: ""
                    val date = document.getTimestamp("date")?.toDate()

                    // Add transaction data to the list
                    val transaction = TransactionModel(
                        id,
                        amount,
                        category,
                        note,
                        date,
                        if (collectionName == COLLECTION_EXPENSE) TransactionType.EXPENSE else TransactionType.INCOME
                    )
                    transactionList.add(transaction)
                }
                callback.invoke(transactionList)
            }
            .addOnFailureListener { e ->
                Log.e("FirebaseTransactionManager", "Error getting $collectionName transactions", e)
            }
    }

    fun loadExpenseTransactions(callback: (List<TransactionModel>) -> Unit) {
        loadTransactions(COLLECTION_EXPENSE, callback)
    }

    fun loadIncomeTransactions(callback: (List<TransactionModel>) -> Unit) {
        loadTransactions(COLLECTION_INCOMES, callback)
    }

    fun getTransactionDetailsIncomeById(
        transactionId: String,
        onComplete: (TransactionModel?) -> Unit
    ) {
        if (transactionId.isEmpty()) {
            onComplete(null)
            return
        }

        val transactionsCollection = firestore.collection("users").document(userId)
            .collection(COLLECTION_INCOMES)

        transactionsCollection.document(transactionId)
            .get()
            .addOnSuccessListener { documentSnapshot ->
                onComplete(mapTransaction(documentSnapshot))
            }
            .addOnFailureListener {
                onComplete(null)
            }
    }

    fun getTransactionDetailsExpenseById(
        transactionId: String,
        onComplete: (TransactionModel?) -> Unit
    ) {
        if (transactionId.isEmpty()) {
            onComplete(null)
            return
        }

        val transactionsCollection = firestore.collection("users").document(userId)
            .collection(COLLECTION_EXPENSE)

        transactionsCollection.document(transactionId)
            .get()
            .addOnSuccessListener { documentSnapshot ->
                onComplete(mapTransaction(documentSnapshot))
            }
            .addOnFailureListener {
                onComplete(null)
            }
    }
}
