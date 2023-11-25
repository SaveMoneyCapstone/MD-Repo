package com.dicoding.savemoney.ui.splash

import android.annotation.*
import android.content.*
import android.os.*
import androidx.appcompat.app.*
import com.dicoding.savemoney.*
import com.dicoding.savemoney.ui.login.*
import com.dicoding.savemoney.ui.main.*


@Suppress("DEPRECATION")
@SuppressLint("CustomSplashScreen")
class SplashScreenActivity : AppCompatActivity() {

    private val splashDelay: Long = 2000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        Handler().postDelayed({
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }, splashDelay)
    }
}