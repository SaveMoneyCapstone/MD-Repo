package com.dicoding.savemoney.ui

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.annotation.RequiresApi
import com.dicoding.savemoney.R
import com.dicoding.savemoney.data.model.TransactionModel
import com.dicoding.savemoney.databinding.ActivityDetailTransactionsBinding
import com.dicoding.savemoney.utils.DateConverter

class DetailTransactionsActivity : AppCompatActivity() {
    private val binding by lazy { ActivityDetailTransactionsBinding.inflate(layoutInflater) }
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

//        val id = intent.getStringExtra(ID)
        val actBar = supportActionBar
        actBar?.title = "Detail Transactions"
        actBar?.setDisplayHomeAsUpEnabled(true)
        
        val detail = intent.getParcelableExtra<TransactionModel>(ID)
        if(detail != null) {
            with(binding) {
                tvCat.text = detail.category
                tvAmount.text = detail.amount.toString()
                tvNotes.text = detail.note
                tvDate.text = DateConverter.convertDate(detail.date)
            }
        }
    }
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun setDetail(userData: TransactionModel) {
        with(binding) {
            tvCat.text = userData.category
            tvAmount.text = userData.amount.toString()
            tvNotes.text = userData.note
            tvDate.text = DateConverter.convertDate(userData.date)
        }
    }

    companion object {
        const val ID = "id"
    }
}