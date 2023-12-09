package com.dicoding.savemoney.ui.login

import android.app.*
import android.content.*
import android.os.*
import android.text.*
import android.util.*
import android.view.*
import android.widget.*
import androidx.activity.*
import androidx.activity.result.contract.*
import androidx.appcompat.app.*
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.*
import com.dicoding.savemoney.*
import com.dicoding.savemoney.data.*
import com.dicoding.savemoney.databinding.*
import com.dicoding.savemoney.ui.main.*
import com.dicoding.savemoney.ui.signup.*
import com.google.android.gms.auth.api.signin.*
import com.google.android.gms.common.api.*
import com.google.firebase.*
import com.google.firebase.auth.*
import kotlinx.coroutines.*

@Suppress("SameParameterValue")
class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private val viewModel: LoginViewModel by viewModels {
        ViewModelFactory.getInstance(this)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupAction()
        setupView()

        binding.registerButtonText.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }

        binding.signInButton.setOnClickListener {
            // login via account google
        }
    }

    private fun setupAction() {
        binding.loginButton.setOnClickListener {
            val email = binding.emailEditText.text.toString()
            val password = binding.passwordEditText.text.toString()

            if (TextUtils.isEmpty(email) && TextUtils.isEmpty(password)) {
                showErrorDialog("Email dan Password harus diisi.")
                return@setOnClickListener
            }

            lifecycleScope.launch {
                when (val result = viewModel.login(email, password)) {
                    is ResultState.Loading -> {
                        isLoading(true)
                    }

                    is ResultState.Success -> {
                        val userModel = result.data
                        isLoading(true)
                        viewModel.saveSession(userModel)
                        AlertDialog.Builder(this@LoginActivity).apply {
                            setTitle("Yeah!")
                            setMessage("Anda berhasil login....")
                            setPositiveButton("Lanjut") { _, _ ->
                                val intent = Intent(this@LoginActivity, MainActivity::class.java)
                                intent.flags =
                                    Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                                startActivity(intent)
                                finish()
                            }
                            create()
                            show()
                        }
                    }

                    is ResultState.Error -> {
                        isLoading(true)
                        AlertDialog.Builder(this@LoginActivity).apply {
                            setTitle("Oops!")
                            setMessage("Login gagal, Periksa email dan kata sandi Anda.")
                            setPositiveButton("Tutup") { dialog, _ ->
                                dialog.dismiss()
                            }
                            create()
                            show()
                        }
                    }
                }
            }
        }
    }

    private fun setupView() {
        @Suppress("DEPRECATION")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }
    }

    @Suppress("SameParameterValue")
    private fun showErrorDialog(message: String) {
        AlertDialog.Builder(this@LoginActivity).apply {
            setTitle("Oops!")
            setMessage(message)
            setPositiveButton("Ok") { dialog, _ ->
                dialog.dismiss()
            }
            create()
            show()
        }
    }
    private fun isLoading(isLoading: Boolean) {
        binding.progressBarLogin.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

}