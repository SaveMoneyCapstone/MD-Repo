package com.dicoding.savemoney.firebase

import com.dicoding.savemoney.utils.DateConverter
import com.google.firebase.Timestamp
import com.google.firebase.auth.*
import com.google.firebase.firestore.*

class FirebaseIncomeManager {
    private val firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()
    private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()

    fun saveIncome(iconCode: Int, amount: Double, category: String, note: String, date: Long, callback: (Boolean) -> Unit) {
        val userId = firebaseAuth.currentUser?.uid
        if (userId != null) {
            val incomeData = hashMapOf(
                "iconCode" to iconCode,
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
}