package com.dicoding.savemoney.firebase

import com.dicoding.savemoney.utils.DateConverter
import com.google.firebase.Timestamp
import com.google.firebase.auth.*
import com.google.firebase.firestore.*

class FirebaseIncomeManager {
    private val firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()
    private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()

    fun saveIncome(amount: Double, category: String, note: String, date: Long, callback: (Boolean) -> Unit) {
        val userId = firebaseAuth.currentUser?.uid
        if (userId != null) {
            val incomeData = hashMapOf(
                "amount" to amount,
                "category" to category,
                "note" to note,
                "date" to Timestamp(date/1000,0)
            )
            firestore.collection("users").document(userId).collection("incomes")
                .add(incomeData)
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

    fun updateTransactionIncome(
        userId: String,
        transactionId: String,
        amount: Double,
        category: String,
        note: String,
        onComplete: (Boolean) -> Unit
    ) {
        val transactionsCollection = firestore.collection("users").document(userId)

        transactionsCollection.collection("incomes").document(transactionId)
            .update(
                mapOf(
                    "amount" to amount,
                    "category" to category,
                    "note" to note
                )
            )
            .addOnSuccessListener {
                onComplete(true)
            }
            .addOnFailureListener {
                onComplete(false)
            }
    }


    fun deleteTransactionIncome(transactionId: String, onComplete: (Boolean) -> Unit) {
        val userId = firebaseAuth.currentUser?.uid
        if (userId != null) {
            val transactionsCollection = firestore.collection("users").document(userId)
                .collection("incomes")

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
