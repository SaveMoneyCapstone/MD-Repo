package com.dicoding.savemoney.ui.signup

import android.content.*
import android.os.*
import android.text.*
import android.view.*
import androidx.activity.*
import androidx.appcompat.app.*
import androidx.core.widget.*
import androidx.lifecycle.*
import com.dicoding.savemoney.*
import com.dicoding.savemoney.data.*
import com.dicoding.savemoney.databinding.*
import com.dicoding.savemoney.ui.login.*
import kotlinx.coroutines.*

@Suppress("DEPRECATION")
class SignUpActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignUpBinding

    private val viewModel: SignupViewModel by viewModels {
        ViewModelFactory.getInstance(this)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbarSignup)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        setupAction()
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

    private fun setupAction() {
        binding.btnRegistrasi.setOnClickListener {
            val name = binding.editNameRegistrasi.text.toString()
            val email = binding.editEmailRegistrasi.text.toString()
            val password = binding.passwordEditText.text.toString()

            lifecycleScope.launch {
                when (val result = viewModel.register(name, email, password)) {
                    is ResultState.Loading -> {
                        binding.progressBarSignup.visibility = View.VISIBLE

                    }

                    is ResultState.Success -> {
                        showSuccessDialog(email)
                        binding.progressBarSignup.visibility = View.GONE
                    }

                    is ResultState.Error -> {
                        showErrorDialog(result.error)
                        binding.progressBarSignup.visibility = View.GONE
                    }
                }
            }
        }
    }


    private fun showSuccessDialog(name: String) {
        AlertDialog.Builder(this).apply {
            setTitle("Yeah!")
            setMessage("Akun dengan $name berhasil didaftarkan. Yuk, login dan explore keuanganmu hari ini")
            setPositiveButton("Lanjut") { _, _ ->
                finish()
            }
            create()
            show()
        }
    }

    private fun showErrorDialog(errorMessage: String) {
        AlertDialog.Builder(this).apply {
            setTitle("Oops!")
            setMessage("Registration failed: $errorMessage")
            setPositiveButton("OK") { _, _ ->
            }
            create()
            show()
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