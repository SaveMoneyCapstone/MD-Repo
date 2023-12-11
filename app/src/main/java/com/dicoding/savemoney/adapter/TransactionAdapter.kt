package com.dicoding.savemoney.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.savemoney.data.local.entity.UserData
import com.dicoding.savemoney.databinding.ItemTransactionsBinding
import com.dicoding.savemoney.firebase.FirebaseDataManager
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import okhttp3.internal.notify

class TransactionAdapter: ListAdapter<UserData, TransactionAdapter.MyViewHolder>(DIFF_CALLBACK) {

    private lateinit var firebaseDataManager: FirebaseDataManager
    inner class MyViewHolder(private val binding: ItemTransactionsBinding) : RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        @RequiresApi(Build.VERSION_CODES.O)
        fun bind(userData: UserData) {
            val currentUser = FirebaseAuth.getInstance().currentUser
            currentUser?.let {
                val userId = it.uid

                with(binding) {
                    firebaseDataManager.getHistory() {amountData, categoryData, notesData, timestamp ->
                        amount.text = amountData.toString()
                        category.text = categoryData.toString()
                        date.text = timestamp.toString()
                    }
                }
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