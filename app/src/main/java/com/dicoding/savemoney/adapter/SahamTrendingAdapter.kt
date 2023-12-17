package com.dicoding.savemoney.adapter

import android.annotation.*
import android.content.*
import android.graphics.Color
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
import kotlin.math.roundToInt

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
                if(resultsItemSaham.close.minus(resultsItemSaham.open) < 0) {
                    binding.tvChange.setTextColor(Color.RED)
                    binding.tvChange.text = context.getString(R.string.change, formatCurrency(resultsItemSaham.close.minus(resultsItemSaham.open)))
                } else {
                    binding.tvChange.setTextColor(Color.GREEN)
                    binding.tvChange.text = context.getString(R.string.change, formatCurrency(resultsItemSaham.close.minus(resultsItemSaham.open)))
                }
            val percent = (resultsItemSaham.close.minus(resultsItemSaham.open))/((resultsItemSaham.close).toDouble())
            val percentage = (percent)*100
            if(percentage < 0) {
                binding.tvPercent.setTextColor(Color.RED)
                binding.tvPercent.text = context.getString(R.string.percent, "%.2f".format(percentage).toString() + "%")
            } else if (percentage == 0.0) {
                binding.tvPercent.setTextColor(Color.GRAY)
                binding.tvPercent.text = context.getString(R.string.percent, "%.2f".format(percentage).toString() + "%")
            } else {
                binding.tvPercent.setTextColor(Color.GREEN)
                binding.tvPercent.text = context.getString(R.string.percent, "%.2f".format(percentage).toString() + "%")
            }


            binding.ivLogo.load(resultsItemSaham.companyLogo) {
                crossfade(true)
                transformations(CircleCropTransformation())
            }
               itemView.setOnClickListener {
                  val intent = Intent(context, DetailSahamTrendingActivity::class.java)
                   intent.putExtra(DetailSahamTrendingActivity.SYMBOL, resultsItemSaham)
                   context.startActivity(intent)
                }
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