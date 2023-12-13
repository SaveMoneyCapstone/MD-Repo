package com.dicoding.savemoney.ui.signup

import android.content.*
import android.os.*
import android.widget.*
import androidx.appcompat.app.*
import com.dicoding.savemoney.databinding.*
import com.dicoding.savemoney.firebase.auth.*
import com.dicoding.savemoney.ui.login.*

class SignUpActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignUpBinding
    private lateinit var authManager: FirebaseAuthManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        authManager = FirebaseAuthManager(this)

        binding.loginButtonText.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

        binding.btnRegistrasi.setOnClickListener {
            val name = binding.editNameRegistrasi.text.toString()
            val email = binding.editEmailRegistrasi.text.toString()
            val password = binding.passwordEditText.text.toString()

            if (name.isEmpty() || email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Harap isi semua kolom", Toast.LENGTH_SHORT).show()
            } else {
                authManager.signUpUser(email, password, name,
                    onSuccess = {
                        Toast.makeText(
                            this,
                            "Pendaftaran berhasil!",
                            Toast.LENGTH_SHORT
                        ).show()
                        val intent = Intent(this, LoginActivity::class.java)
                        startActivity(intent)
                        finish()
                    },
                    onFailure = { errorMessage ->
                        Toast.makeText(
                            this,
                            errorMessage,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                )
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return true
    }
}
