package com.dicoding.savemoney.ui.add

import android.os.*
import android.view.View
import android.widget.TextView
import androidx.activity.viewModels
import androidx.annotation.*
import androidx.appcompat.app.*
import com.dicoding.savemoney.*
import com.dicoding.savemoney.databinding.*
import com.dicoding.savemoney.ui.ViewModelFactory
import com.dicoding.savemoney.ui.adapter.AddSectionPagerAdapter
import com.dicoding.savemoney.utils.DatePickerFragment
import com.google.android.material.tabs.*
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class AddExpenseActivity : AppCompatActivity(), DatePickerFragment.DialogDateListener {
    private var dueDateMillis: Long = System.currentTimeMillis()
//    private val viewModel by viewModels<AddExpenseViewModel> {
//       ViewModelFactory.getInstance(this)
//    }
    private lateinit var binding: ActivityAddExpenseBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddExpenseBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val sectionPagerAdapter = AddSectionPagerAdapter(this)
        binding.viewPager.adapter = sectionPagerAdapter
        TabLayoutMediator(
            binding.tabs, binding.viewPager
        ) { tab: TabLayout.Tab, position: Int ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()
        supportActionBar?.elevation = 0f
        supportActionBar?.title = getString(R.string.tambah_data)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return true
    }
    companion object {
        @StringRes
        private val TAB_TITLES = intArrayOf(R.string.title_pager_income, R.string.title_pager_expense)
    }

    fun showDatePicker(view: View) {
        val dialogFragment = DatePickerFragment()
        dialogFragment.show(supportFragmentManager, "datePicker")
    }

    override fun onDialogDateSet(tag: String?, year: Int, month: Int, dayOfMonth: Int) {
        val calendar = Calendar.getInstance()
        calendar.set(year, month, dayOfMonth)
        val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        findViewById<TextView>(R.id.add_tv_due_date).text = dateFormat.format(calendar.time)

        dueDateMillis = calendar.timeInMillis
    }
}