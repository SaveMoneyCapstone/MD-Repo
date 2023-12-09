package com.dicoding.savemoney.ui.splash

import android.annotation.*
import android.content.*
import android.os.*
import android.view.*
import androidx.appcompat.app.*
import androidx.lifecycle.*
import com.dicoding.savemoney.*
import com.dicoding.savemoney.data.preference.*
import com.dicoding.savemoney.databinding.*
import com.dicoding.savemoney.ui.login.*
import com.dicoding.savemoney.ui.main.*


@Suppress("DEPRECATION")
@SuppressLint("CustomSplashScreen")
class SplashScreenActivity : AppCompatActivity() {
    private lateinit var authPreference: AuthPreference
    private lateinit var binding: ActivitySplashScreenBinding

    private val splashDelay: Long = 2000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        authPreference = AuthPreference.getInstance(this.dataStore)

        checkUserSession()
        setupView()

        Handler().postDelayed({
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }, splashDelay)
    }

    private fun checkUserSession() {
        authPreference.getSession().asLiveData().observe(this) { userModel ->
            if (userModel != null && userModel.isLogin) {
                startActivity(Intent(this, MainActivity::class.java))
                finish()
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
}