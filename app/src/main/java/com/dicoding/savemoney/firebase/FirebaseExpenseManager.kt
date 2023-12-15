package com.dicoding.savemoney.firebase

import com.google.firebase.auth.*
import com.google.firebase.firestore.*

class FirebaseExpenseManager {
    private val firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()
    private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()

    

    fun saveExpense(amount: Double, category: String, note: String, callback: (Boolean) -> Unit) {
        val userId = firebaseAuth.currentUser?.uid
        if (userId != null) {
            val expenseData = hashMapOf(
                "amount" to amount,
                "category" to category,
                "note" to note,
                "timestamp" to FieldValue.serverTimestamp()
            )

            firestore.collection("users").document(userId).collection("expense")
                .add(expenseData)
                .addOnSuccessListener {
                    callback.invoke(true)
                }
                .addOnFailureListener {
                    callback.invoke(false)
                    it.printStackTrace()
                }
        } else {
            callback.invoke(false)
        }
    }

    fun updateTransactionExpense(
        userId: String,
        transactionId: String,
        amount: Double,
        category: String,
        note: String,
        onComplete: (Boolean) -> Unit
    ) {
        val transactionsCollection = firestore.collection("users").document(userId)

        transactionsCollection.collection("expense").document(transactionId)
            .update(
                mapOf(
                    "amount" to amount,
                    "category" to category,
                    "note" to note
                )
            )
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    onComplete(true)
                } else {
                    onComplete(false)
                }
            }
    }

    fun deleteTransactionExpense(transactionId: String, onComplete: (Boolean) -> Unit) {
        val userId = firebaseAuth.currentUser?.uid
        if (userId != null) {
            val transactionsCollection = firestore.collection("users").document(userId)
                .collection("expense")

            transactionsCollection.document(transactionId)
                .delete()
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        onComplete(true)
                    } else {
                        onComplete(false)
                    }
                }
        } else {
            onComplete(false)
        }
    }

}

