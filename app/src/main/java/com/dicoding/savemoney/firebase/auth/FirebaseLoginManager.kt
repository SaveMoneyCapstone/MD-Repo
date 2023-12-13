package com.dicoding.savemoney.firebase.auth

import android.app.*
import android.content.*
import android.util.*
import android.widget.*
import com.dicoding.savemoney.R
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
                    showToast(context.getString(R.string.login_berhasil))
                    onSuccess.invoke()
                } else {
                    // Login gagal
                    Log.e("FirebaseAuthManager", "Login gagal", task.exception)
                    showToast(context.getString(R.string.login_gagal))
                    onFailure.invoke()
                }
            }
    }

    private fun showToast(message: String) {
        (context as? Activity)?.runOnUiThread {
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
        }
    }
}
