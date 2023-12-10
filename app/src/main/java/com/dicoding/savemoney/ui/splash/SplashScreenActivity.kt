package com.dicoding.savemoney.ui.splash

import android.annotation.*
import android.content.*
import android.os.*
import android.view.*
import androidx.appcompat.app.*
import androidx.lifecycle.*
import com.dicoding.savemoney.*
import com.dicoding.savemoney.databinding.*
import com.dicoding.savemoney.ui.login.*
import com.dicoding.savemoney.ui.main.*


@Suppress("DEPRECATION")
@SuppressLint("CustomSplashScreen")
class SplashScreenActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySplashScreenBinding

    private val splashDelay: Long = 2000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupView()

        Handler().postDelayed({
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }, splashDelay)
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