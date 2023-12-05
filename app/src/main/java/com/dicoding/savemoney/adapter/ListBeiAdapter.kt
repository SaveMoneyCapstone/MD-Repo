package com.dicoding.savemoney.adapter
import android.view.*
import androidx.recyclerview.widget.*
import coil.*
import coil.transform.*
import com.dicoding.savemoney.data.response.*
import com.dicoding.savemoney.databinding.*

class ListBeiAdapter : ListAdapter<ResultsItem, ListBeiAdapter.BeiViewHolder>(DIFF_CALLBACK) {
    class BeiViewHolder(
        private val binding: ItemBeiCompanyBinding,
    ) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(result: ResultsItem) {
            binding.tvName.text = result.name
            binding.tvTicker.text = result.symbol

            binding.ivLogo.load(result.logo) {
                crossfade(true)
                transformations(CircleCropTransformation())
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BeiViewHolder {
        val binding =
            ItemBeiCompanyBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BeiViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BeiViewHolder, position: Int) {
        val stockCompany = getItem(position)
        holder.bind(stockCompany)
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ResultsItem>() {
            override fun areItemsTheSame(
                oldItem: ResultsItem,
                newItem: ResultsItem
            ): Boolean {
                return oldItem.symbol == newItem.symbol
            }

            override fun areContentsTheSame(
                oldItem: ResultsItem,
                newItem: ResultsItem
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}
