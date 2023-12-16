package com.dicoding.savemoney.adapter

import android.annotation.*
import android.content.*
import android.view.*
import androidx.recyclerview.widget.*
import coil.*
import coil.transform.*
import com.dicoding.savemoney.*
import com.dicoding.savemoney.data.response.*
import com.dicoding.savemoney.databinding.*
import com.dicoding.savemoney.ui.fragment.ojk.*
import com.dicoding.savemoney.ui.fragment.sahamtrending.*
import com.dicoding.savemoney.utils.*

class SahamTrendingAdapter :
    ListAdapter<RecomendationsItem, SahamTrendingAdapter.ViewHolder>(
        DIFF_CALLBACK
    ) {
    class ViewHolder(
        private var binding: ItemSahamTrendingBinding,
        private val context: Context
    ) :
        RecyclerView.ViewHolder(binding.root) {


        @SuppressLint("SetTextI18n")
        fun bind(resultsItemSaham: RecomendationsItem) {

            binding.tvSymbol.text = resultsItemSaham.symbol
            binding.tvName.text = resultsItemSaham.companyName
            binding.tvClose.text =
                context.getString(R.string.close, formatCurrency(resultsItemSaham.close ?: 0))
            binding.tvChange.text =
                context.getString(R.string.change, formatCurrency(resultsItemSaham.close.minus(resultsItemSaham.open)))
            val change_percent = resultsItemSaham.close.minus(resultsItemSaham.open).div(resultsItemSaham.open)
            binding.tvPercent.text =
                context.getString(R.string.percent, "$change_percent %")

            binding.ivLogo.load(resultsItemSaham.companyLogo) {
                crossfade(true)
                transformations(CircleCropTransformation())
            }
    //            itemView.setOnClickListener {
    //                val intent = Intent(context, DetailSahamTrendingActivity::class.java)
    //                intent.putExtra(DetailSahamTrendingActivity.TAG, resultsItemSaham)
    //                context.startActivity(intent)
    //            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val binding =
            ItemSahamTrendingBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding, parent.context)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val dataItem = getItem(position)
        holder.bind(dataItem)
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<RecomendationsItem>() {
            override fun areItemsTheSame(
                oldItem: RecomendationsItem,
                newItem: RecomendationsItem
            ): Boolean {
                return oldItem.symbol == newItem.symbol
            }

            override fun areContentsTheSame(
                oldItem: RecomendationsItem,
                newItem: RecomendationsItem
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}