package com.dicoding.savemoney.firebase.auth

import android.content.*
import android.util.*
import android.widget.*
import com.google.firebase.auth.*

class FirebaseLoginManager(private val context: Context) {

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    fun loginUser(
        email: String,
        password: String,
        onSuccess: () -> Unit,
        onFailure: () -> Unit
    ) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // Login berhasil
                    Toast.makeText(context, "Login berhasil", Toast.LENGTH_SHORT).show()
                    onSuccess.invoke()
                } else {
                    // Login gagal
                    Log.e("FirebaseAuthManager", "Login gagal", task.exception)
                    Toast.makeText(context, "Login gagal", Toast.LENGTH_SHORT).show()
                    onFailure.invoke()
                }
            }
    }
}