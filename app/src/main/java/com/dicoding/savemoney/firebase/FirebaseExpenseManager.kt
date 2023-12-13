package com.dicoding.savemoney.firebase

import android.os.Build
import androidx.annotation.RequiresApi
import com.dicoding.savemoney.utils.DateConverter
import com.google.firebase.Timestamp
import com.google.firebase.auth.*
import com.google.firebase.firestore.*
import java.util.Date

class FirebaseExpenseManager {
    private val firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()
    private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()


    @RequiresApi(Build.VERSION_CODES.O)
    fun saveExpense(iconCode: Int, amount: Double, category: String, note: String, date: Long, callback: (Boolean) -> Unit) {
        val userId = firebaseAuth.currentUser?.uid
        if (userId != null) {
            val expenseData = hashMapOf(
                "iconCode" to iconCode,
                "amount" to amount,
                "category" to category,
                "note" to note,
                "date" to Timestamp(date/1000,0)
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
}