package com.dicoding.savemoney.adapter

import com.dicoding.savemoney.ui.detail.DetailTransactionActivity
import android.annotation.SuppressLint
import android.content.*
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.savemoney.*
import com.dicoding.savemoney.data.model.TransactionModel
import com.dicoding.savemoney.databinding.ItemTransactionBinding
import com.dicoding.savemoney.utils.*
import java.text.SimpleDateFormat
import java.util.*

@Suppress("DEPRECATION")
class TransactionAdapter(
    private val transactions: MutableList<TransactionModel>,
    private val context: Context,
) :
    RecyclerView.Adapter<TransactionAdapter.TransactionViewHolder>() {

    private val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())

    @SuppressLint("NotifyDataSetChanged")
    fun updateTransactions(updatedTransactions: List<TransactionModel>) {
        transactions.clear()
        transactions.addAll(updatedTransactions)
        notifyDataSetChanged()
    }

    inner class TransactionViewHolder(private val binding: ItemTransactionBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val transaction = transactions[position]
                    navigateToTransactionDetail(transaction)
                }
            }
        }

        fun bind(transaction: TransactionModel) {
            val sign = if (transaction.transactionType == TransactionType.EXPENSE) "-" else "+"
            val formattedAmount = "$sign${formatCurrencyTransaction(transaction.amount)}"

            binding.tvAmount.text = formattedAmount
            binding.tvCategory.text = transaction.category
            binding.tvTimestamp.text = dateFormat.format(transaction.timestamp ?: Date())

            val arrowIconRes = if (transaction.transactionType == TransactionType.EXPENSE) {
                R.drawable.ic_transaction_type_expense
            } else {
                R.drawable.ic_transaction_type_income
            }
            binding.imageViewArrow.setImageResource(arrowIconRes)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransactionViewHolder {
        val binding =
            ItemTransactionBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TransactionViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TransactionViewHolder, position: Int) {
        val transaction = transactions[position]
        holder.bind(transaction)
    }

    override fun getItemCount(): Int {
        return transactions.size
    }

    private fun navigateToTransactionDetail(transaction: TransactionModel) {
        val intent = Intent(context, DetailTransactionActivity::class.java)
        intent.putExtra("TRANSACTION_ID", transaction.id)
        context.startActivity(intent)
    }
}
