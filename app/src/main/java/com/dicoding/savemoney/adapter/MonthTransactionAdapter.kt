package com.dicoding.savemoney.adapter

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.savemoney.R
import com.dicoding.savemoney.data.model.TransactionModel
import com.dicoding.savemoney.databinding.ItemTransactionsBinding
import com.dicoding.savemoney.ui.DetailTransactionsActivity
import com.dicoding.savemoney.utils.DateConverter
import com.dicoding.savemoney.utils.RupiahConverter
import com.dicoding.savemoney.utils.TransactionType


class MonthTransactionAdapter: ListAdapter<TransactionModel, MonthTransactionAdapter.MyViewHolder>(DIFF_CALLBACK) {
    inner class MyViewHolder(private val binding: ItemTransactionsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        @RequiresApi(Build.VERSION_CODES.O)
        fun bind(userData: TransactionModel) {
            with(binding) {
                binding.amount.text = if (userData.transactionType == TransactionType.EXPENSE) {
                    "-" + RupiahConverter.convertToRupiah(userData.amount)
                } else {
                    "+" + RupiahConverter.convertToRupiah(userData.amount)
                }

                val arrowIconRes = if (userData.transactionType == TransactionType.EXPENSE) {
                    R.drawable.expenses
                } else {
                    R.drawable.income
                }
                binding.icon.setImageResource(arrowIconRes)
                category.text = userData.category.toString()
                date.text = DateConverter.convertDate(userData.date)
            }
            itemView.setOnClickListener {
                val intent = Intent(itemView.context, DetailTransactionsActivity::class.java)
                intent.putExtra(DetailTransactionsActivity.ID, userData)
                it.context.startActivity(intent)
            }
        }
    }

    //    @SuppressLint("NotifyDataSetChanged")
//    fun updateTransactions(updatedTransactions: MutableList<TransactionModel>) {
//        transactions.clear()
//        transactions.addAll(updatedTransactions)
//        notifyDataSetChanged()
//    }
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
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<TransactionModel>() {

            override fun areItemsTheSame(
                oldItem: TransactionModel,
                newItem: TransactionModel
            ): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: TransactionModel,
                newItem: TransactionModel
            ): Boolean {
                return oldItem.id == newItem.id
            }
        }
    }
}
