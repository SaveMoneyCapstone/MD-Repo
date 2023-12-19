package com.dicoding.savemoney.ui.add

import android.app.NotificationManager
import android.content.Context
import android.os.*
import android.widget.TextView
import androidx.annotation.*
import androidx.appcompat.app.*
import com.dicoding.savemoney.*
import com.dicoding.savemoney.databinding.*
import com.dicoding.savemoney.utils.DatePickerFragment
import com.google.android.material.tabs.*
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class AddTransactionActivity : AppCompatActivity(), DatePickerFragment.DialogDateListener {

    private lateinit var binding: ActivityAddTransactionBinding
    private lateinit var notificationManager : NotificationManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddTransactionBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val sectionPagerAdapter = SectionPagerAdapter(this)
        binding.viewPager.adapter = sectionPagerAdapter
        TabLayoutMediator(
            binding.tabs, binding.viewPager
        ) { tab: TabLayout.Tab, position: Int ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()
        supportActionBar?.elevation = 0f
        supportActionBar?.title = getString(R.string.tambah_data)
        notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return true
    }
    override fun onDialogDateSet(tag: String?, year: Int, month: Int, dayOfMonth: Int) {
        val calendar = Calendar.getInstance()
        calendar.set(year, month, dayOfMonth)
        val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        findViewById<TextView>(R.id.add_tv_due_date).text = dateFormat.format(calendar.time)

        dueDateMillis = dateFormat.calendar.timeInMillis
    }

    companion object {
        @StringRes
        private val TAB_TITLES = intArrayOf(R.string.title_pager_income, R.string.title_pager_expense)


        var dueDateMillis: Long = System.currentTimeMillis()
    }
}