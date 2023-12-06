package com.dicoding.savemoney.adapter

import android.content.*
import android.view.*
import androidx.recyclerview.widget.*
import com.dicoding.savemoney.data.response.*
import com.dicoding.savemoney.databinding.*
import com.dicoding.savemoney.ui.fragment.ojk.*

class OjkInvestmentAdapter :
    ListAdapter<AppsItem, OjkInvestmentAdapter.ViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemOjkInvestmentBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding, parent.context)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val dataItem = getItem(position)
        holder.bind(dataItem)
    }

    class ViewHolder(
        private val binding: ItemOjkInvestmentBinding,
        private val context: Context
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(dataItem: AppsItem) {
            binding.tvNameOjk.text = dataItem.name
            binding.tvOwnerOjk.text = dataItem.owner

            itemView.setOnClickListener {
                val intent = Intent(context, DetailOjkInvestmentActivity::class.java)
                intent.putExtra(DetailOjkInvestmentActivity.EXTRA_DATA, dataItem)
                context.startActivity(intent)
            }
        }
    }

    private class DiffCallback : DiffUtil.ItemCallback<AppsItem>() {
        override fun areItemsTheSame(oldItem: AppsItem, newItem: AppsItem): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: AppsItem, newItem: AppsItem): Boolean {
            return oldItem == newItem
        }
    }
}
