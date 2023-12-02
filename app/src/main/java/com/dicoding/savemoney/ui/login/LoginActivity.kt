package com.dicoding.savemoney.ui.login

import android.app.*
import android.content.*
import android.os.*
import android.text.*
import android.util.*
import android.widget.*
import androidx.activity.result.contract.*
import androidx.appcompat.app.*
import com.dicoding.savemoney.databinding.*
import com.dicoding.savemoney.ui.main.*
import com.dicoding.savemoney.ui.signup.*
import com.google.android.gms.auth.api.signin.*
import com.google.android.gms.common.api.*
import com.google.firebase.*
import com.google.firebase.auth.*

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupAction()

        binding.registerButtonText.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }

        binding.loginButton.setOnClickListener {
            setupAction()
        }

        binding.signInButton.setOnClickListener {
            // login via account google
        }
    }

    private fun setupAction() {
        binding.loginButton.setOnClickListener {
            val email = binding.emailEditText.text?.toString()
            val password = binding.passwordEditText.text?.toString()
            if (TextUtils.isEmpty(email) && TextUtils.isEmpty(password)) {
                showErrorDialog("Email dan Password harus diisi.")
                return@setOnClickListener
            } else {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }
        }
    }

    @Suppress("SameParameterValue")
    private fun showErrorDialog(messageError: String) {
        Toast.makeText(this, messageError, Toast.LENGTH_SHORT).show()
    }
}