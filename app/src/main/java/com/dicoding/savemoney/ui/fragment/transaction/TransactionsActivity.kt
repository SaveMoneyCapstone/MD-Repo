package com.dicoding.savemoney.ui.fragment.transaction

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.savemoney.R
import com.dicoding.savemoney.databinding.ActivityTransactionsBinding
import com.dicoding.savemoney.databinding.FragmentAddExpensesBinding
import com.dicoding.savemoney.ui.ViewModelFactory
import com.dicoding.savemoney.ui.adapter.RecentTransactionsAdapter
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.format.DateTimeFormatter
import java.util.Calendar
import java.util.Date
import java.util.Locale

class TransactionsActivity : AppCompatActivity() {
    private val viewModel: TransactionViewModel by viewModels() {
        ViewModelFactory.getInstance(this)
    }

    private var firstDateMillis: Long = System.currentTimeMillis()
    private var endDateMillis: Long = System.currentTimeMillis()
    private val binding by lazy { ActivityTransactionsBinding.inflate(layoutInflater)}
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val calendar = Calendar.getInstance()
        val month = calendar.get(Calendar.MONTH)+1
        val year = calendar.get(Calendar.YEAR)
        val first= "$year-$month-01"
        var date: Date
        val formatter = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        date = formatter.parse(first)
        firstDateMillis = date.time
        val end = "$year-$month-31"
        var endDate: Date
        val formatterend = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        endDate = formatterend.parse(end)
        endDateMillis = endDate.time
        val layoutManager = LinearLayoutManager(this)
        binding.rvRecent.layoutManager = layoutManager
        viewModel.getDataByMonth(firstDateMillis,endDateMillis).observe(this) {
            val adapter = RecentTransactionsAdapter()
            binding.rvRecent.adapter = adapter
            adapter.submitList(it)
        }
    }
}