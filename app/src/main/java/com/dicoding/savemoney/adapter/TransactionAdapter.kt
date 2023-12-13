package com.dicoding.savemoney.adapter

import android.annotation.*
import android.view.*
import android.widget.*
import androidx.recyclerview.widget.*
import com.dicoding.savemoney.*
import com.dicoding.savemoney.data.model.*
import com.dicoding.savemoney.utils.*
import java.text.*
import java.util.*

class TransactionAdapter(
    private val transactions: MutableList<TransactionModel>
) :
    RecyclerView.Adapter<TransactionAdapter.TransactionViewHolder>() {

    private val dateFormat = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())

    @SuppressLint("NotifyDataSetChanged")
    fun updateTransactions(updatedTransactions: List<TransactionModel>) {
        transactions.clear()
        transactions.addAll(updatedTransactions)
        notifyDataSetChanged()
    }

    inner class TransactionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textViewAmount: TextView = itemView.findViewById(R.id.tvAmount)
        val textViewCategory: TextView = itemView.findViewById(R.id.tvCategory)
        val textViewNote: TextView = itemView.findViewById(R.id.tvNote)
        val textViewTimestamp: TextView = itemView.findViewById(R.id.tvTimestamp)
        val imageArrow: ImageView = itemView.findViewById(R.id.imageViewArrow)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransactionViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_transaction, parent, false)
        return TransactionViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: TransactionViewHolder, position: Int) {
        val transaction = transactions[position]

        holder.textViewAmount.text = formatCurrencyTransaction(transaction.amount)
        holder.textViewCategory.text = transaction.category
        holder.textViewNote.text = transaction.note
        holder.textViewTimestamp.text = dateFormat.format(transaction.timestamp ?: Date())

        val arrowIconRes = if (transaction.transactionType == TransactionType.EXPENSE) {
            R.drawable.ic_red_arrow
        } else {
            R.drawable.ic_green_arrow
        }
        holder.imageArrow.setImageResource(arrowIconRes)
    }

    override fun getItemCount(): Int {
        return transactions.size
    }
}
