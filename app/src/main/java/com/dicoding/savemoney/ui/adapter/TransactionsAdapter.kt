package com.dicoding.savemoney.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.savemoney.data.UserData
import com.dicoding.savemoney.databinding.ItemRecentDashboardBinding
import com.dicoding.savemoney.ui.adapter.RecentTransactionsAdapter.Companion.DIFF_CALLBACK

class TransactionsAdapter: ListAdapter<UserData, TransactionsAdapter.MyViewHolder>(DIFF_CALLBACK) {
    override fun onBindViewHolder(holder: TransactionsAdapter.MyViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

    inner class MyViewHolder(private val binding: ItemRecentDashboardBinding) : RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): TransactionsAdapter.MyViewHolder {
        val binding = ItemRecentDashboardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }


}