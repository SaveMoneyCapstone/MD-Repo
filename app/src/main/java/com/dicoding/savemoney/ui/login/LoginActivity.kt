package com.dicoding.savemoney.ui.login

import android.content.*
import android.os.*
import android.text.*
import android.widget.*
import androidx.appcompat.app.*
import com.dicoding.savemoney.databinding.*
import com.dicoding.savemoney.ui.signup.*

@Suppress("SameParameterValue")
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
    }

    private fun setupAction() {
        binding.loginButton.setOnClickListener {
            val email = binding.emailEditText.text?.toString()
            val password = binding.passwordEditText.text?.toString()
            if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
                showErrorDialog("Email dan Password harus diisi.")
                return@setOnClickListener
            }
        }
    }

    private fun showErrorDialog(messageError: String) {
        Toast.makeText(this, messageError, Toast.LENGTH_SHORT).show()
    }
}