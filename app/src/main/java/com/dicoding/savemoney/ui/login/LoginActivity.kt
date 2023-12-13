package com.dicoding.savemoney.ui.login

import android.content.*
import android.os.*
import android.text.*
import android.widget.*
import androidx.appcompat.app.*
import com.dicoding.savemoney.data.preference.*
import com.dicoding.savemoney.databinding.*
import com.dicoding.savemoney.firebase.auth.*
import com.dicoding.savemoney.ui.main.*
import com.dicoding.savemoney.ui.signup.*

class LoginActivity : AppCompatActivity() {

    private lateinit var authManager: FirebaseLoginManager
    private lateinit var binding: ActivityLoginBinding
    private lateinit var userSessionManager: UserSessionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        authManager = FirebaseLoginManager(this)
        userSessionManager = UserSessionManager(this)

        binding.registerButtonText.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }

        binding.forgotPassword.setOnClickListener {
            val intent = Intent(this, ForgotPasswordActivity::class.java)
            startActivity(intent)
        }

        binding.loginButton.setOnClickListener {
            val email = binding.emailEditText.text.toString()
            val password = binding.passwordEditText.text.toString()

            if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
                Toast.makeText(this, "Harap masukkan email dan password", Toast.LENGTH_SHORT).show()
            } else {
                authManager.loginUser(email, password,
                    onSuccess = {
                        // Login berhasil
                        userSessionManager.setUserLoggedIn(true)
                        val intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)
                        finish()
                    },
                    onFailure = {
                        Toast.makeText(
                            this,
                            "Login gagal. Periksa kembali email dan password.",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                )
            }
        }
    }
}
