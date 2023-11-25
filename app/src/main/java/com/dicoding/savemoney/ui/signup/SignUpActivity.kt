package com.dicoding.savemoney.ui.signup

import android.content.*
import android.os.*
import android.text.*
import android.view.*
import androidx.appcompat.app.*
import androidx.core.widget.*
import androidx.lifecycle.*
import com.dicoding.savemoney.data.*
import com.dicoding.savemoney.databinding.*
import com.dicoding.savemoney.ui.login.*
import kotlinx.coroutines.*

@Suppress("DEPRECATION")
class SignUpActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignUpBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        setMyButtonEnable()
        setupView()

        binding.apply {
            editNameRegistrasi.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    setMyButtonEnable()
                }

                override fun afterTextChanged(s: Editable?) {}

            })

            editEmailRegistrasi.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    setMyButtonEnable()
                }

                override fun afterTextChanged(s: Editable?) {}

            })

            passwordEditText.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {

                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    setMyButtonEnable()
                }

                override fun afterTextChanged(s: Editable?) {
                }

            })
        }

        binding.loginButtonText.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }

    private fun setMyButtonEnable() {
        val name = binding.editNameRegistrasi.text?.toString()
        val email = binding.editEmailRegistrasi.text?.toString()
        val password = binding.passwordEditText.text?.toString()

        val isNameEmpty = name.isNullOrBlank()
        val isEmailEmpty = email.isNullOrBlank()
        val isPasswordEmpty = password.isNullOrBlank()

        binding.btnRegistrasi.isEnabled = !isNameEmpty && !isEmailEmpty && !isPasswordEmpty
    }

    private fun setupView() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return true
    }
}