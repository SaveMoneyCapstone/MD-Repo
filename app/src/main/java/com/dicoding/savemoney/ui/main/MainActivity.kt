package com.dicoding.savemoney.ui.main

import android.Manifest
import android.annotation.*
import android.content.*
import android.os.*
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.*
import androidx.navigation.*
import androidx.navigation.ui.*
import com.dicoding.savemoney.R
import com.dicoding.savemoney.data.preference.*
import com.dicoding.savemoney.ui.splash.*
import com.google.android.material.bottomnavigation.*
class MainActivity : AppCompatActivity() {
    private lateinit var userSessionManager: UserSessionManager

    @SuppressLint("MissingInflatedId", "RestrictedApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        userSessionManager = UserSessionManager(this)
        if (!userSessionManager.isUserLoggedIn()) {
            val intent = Intent(this, SplashScreenActivity::class.java)
            startActivity(intent)
            finish()
        }
        val requestPermissionLauncher =
            registerForActivityResult(
                ActivityResultContracts.RequestPermission()
            ) { isGranted: Boolean ->
                if (isGranted) {
                    showToast("Notifications permission granted")
                } else {
                    showToast("Notifications will not show without permission")
                }
            }
        if (Build.VERSION.SDK_INT > 32) {
            requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
        }

        val navView: BottomNavigationView = findViewById(R.id.nav_view)

        val navController = findNavController(R.id.nav_host_fragment)

        val appBarConfiguration = AppBarConfiguration.Builder(
            R.id.navigation_dashboard,
            R.id.navigation_transaction,
            R.id.navigation_transaction,
            R.id.navigation_saham,
            R.id.navigation_other
        ).build()

        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)


        navController.addOnDestinationChangedListener { _, destination, _ ->
            supportActionBar?.title = when (destination.id) {
                R.id.navigation_dashboard -> "Dashboard"
                R.id.navigation_transaction -> "Transactions History"
                R.id.navigation_saham -> "Stock Recommendation"
                R.id.navigation_other -> "Investment Portal OJK"
                else -> "Save Money"
            }
        }
    }
    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}
