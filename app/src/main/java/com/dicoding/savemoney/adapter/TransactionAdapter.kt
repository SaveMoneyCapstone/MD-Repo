package com.dicoding.savemoney.adapter

import android.annotation.SuppressLint
import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.savemoney.R
import com.dicoding.savemoney.data.Transaction
import com.dicoding.savemoney.databinding.ItemTransactionsBinding
import com.dicoding.savemoney.firebase.FirebaseDataManager
import com.dicoding.savemoney.utils.DateConverter
import com.dicoding.savemoney.utils.RupiahConverter

class TransactionAdapter: ListAdapter<Transaction, TransactionAdapter.MyViewHolder>(DIFF_CALLBACK) {

    private lateinit var firebaseDataManager: FirebaseDataManager

    inner class MyViewHolder(private val binding: ItemTransactionsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        @RequiresApi(Build.VERSION_CODES.O)
        fun bind(userData: Transaction) {
           with(binding) {
               when(userData.iconCode) {
                   0 -> {
                       icon.setImageResource(R.drawable.income)
                       amount.text = "+" + RupiahConverter.convertToRupiah(userData.amount)
                   }
                   1 -> {
                       amount.text = "-" + RupiahConverter.convertToRupiah(userData.amount)
                       icon.setImageResource(R.drawable.expenses)
                   }
               }
               category.text = userData.category.toString()
               date.text = DateConverter.convertDate(userData.date)
           }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding =
            ItemTransactionsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val user = getItem(position)
        holder.bind(user)
    }


    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Transaction>() {

            override fun areItemsTheSame(oldItem: Transaction, newItem: Transaction): Boolean {
                TODO("Not yet implemented")
            }

            override fun areContentsTheSame(oldItem: Transaction, newItem: Transaction): Boolean {
                TODO("Not yet implemented")
            }
        }
    }
}