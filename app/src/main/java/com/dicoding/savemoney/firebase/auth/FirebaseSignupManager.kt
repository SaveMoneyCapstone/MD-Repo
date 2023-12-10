package com.dicoding.savemoney.firebase.auth

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class FirebaseAuthManager {

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()

    fun signUpUser(
        email: String,
        password: String,
        name: String,
        onSuccess: () -> Unit,
        onFailure: (String) -> Unit
    ) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val userId = auth.currentUser?.uid
                    val userMap = hashMapOf(
                        "name" to name,
                        "email" to email
                    )
                    if (userId != null) {
                        firestore.collection("users").document(userId)
                            .set(userMap)
                            .addOnSuccessListener {
                                onSuccess.invoke()
                            }
                            .addOnFailureListener { _ ->
                                onFailure.invoke("Failed to save user data.")
                            }
                    }
                } else {
                    Log.e("FirebaseAuthManager", "Registration failed", task.exception)
                    onFailure.invoke("Registration failed: ${task.exception?.message}")
                }
            }
    }
}
