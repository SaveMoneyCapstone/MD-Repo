package com.dicoding.savemoney.firebase

import com.google.firebase.auth.*
import com.google.firebase.firestore.*

class FirebaseIncomeManager {
    private val firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()
    private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()

    fun saveIncome(amount: Double, category: String, note: String, callback: (Boolean) -> Unit) {
        val userId = firebaseAuth.currentUser?.uid
        if (userId != null) {
            val incomeData = hashMapOf(
                "amount" to amount,
                "category" to category,
                "note" to note,
                "timestamp" to FieldValue.serverTimestamp()
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
}