package com.dicoding.savemoney.ui.adapter


import android.annotation.SuppressLint
import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.savemoney.R
import androidx.recyclerview.widget.DiffUtil
import com.dicoding.savemoney.DateConverter
import com.dicoding.savemoney.RupiahConverter
import com.dicoding.savemoney.data.UserData
import com.dicoding.savemoney.databinding.ItemRecentDashboardBinding

class RecentTransactionsAdapter : ListAdapter<UserData, RecentTransactionsAdapter.MyViewHolder>(DIFF_CALLBACK) {


    inner class MyViewHolder(private val binding: ItemRecentDashboardBinding) : RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        @RequiresApi(Build.VERSION_CODES.O)
        fun bind(userData: UserData) {
            with(binding) {
                category.text = userData.category
                date.text = DateConverter.convertMillisToString(userData.date)

                when(userData.code) {
                    0 -> {
                        icon.setImageResource(R.drawable.income)
                        amount.text = "+" + RupiahConverter.convertToRupiah(userData.amount)
                    }
                    1 -> {
                        amount.text = "-" + RupiahConverter.convertToRupiah(userData.amount)
                        icon.setImageResource(R.drawable.expenses)
                    }
                }
            }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding =
        ItemRecentDashboardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<UserData>() {
            override fun areItemsTheSame(oldItem: UserData, newItem: UserData): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: UserData, newItem: UserData): Boolean {
                return oldItem.id == newItem.id
            }
        }
    }

}