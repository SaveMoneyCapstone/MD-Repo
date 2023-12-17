package com.dicoding.savemoney.adapter

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.savemoney.data.model.PostsItem
import com.dicoding.savemoney.databinding.ItemListNewsBinding
import com.dicoding.savemoney.ui.DetailNewsActivity
import com.dicoding.savemoney.utils.DateConverter
import java.util.TimeZone

class NewsAdapter : androidx.recyclerview.widget.ListAdapter<PostsItem, NewsAdapter.MyViewHolder>(DIFF_CALLBACK) {
    inner class MyViewHolder(val binding: ItemListNewsBinding) : RecyclerView.ViewHolder(binding.root) {
        @RequiresApi(Build.VERSION_CODES.O)
        fun bind(item: PostsItem) {
            with(binding) {
                tvItemTitle.text = item.title
                tvItemPublishedDate.text = DateConverter.formatDate(item.pubDate, TimeZone.getDefault().id)
                Glide.with(itemView.context)
                    .load(item.thumbnail)
                    .into(imgPoster)
            }
            itemView.setOnClickListener {
                val intent = Intent(itemView.context, DetailNewsActivity::class.java)
                intent.putExtra(DetailNewsActivity.TITLE, item)
                it.context.startActivity(intent)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemListNewsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val news = getItem(position)
        holder.bind(news)
    }

    companion object {
            val DIFF_CALLBACK: DiffUtil.ItemCallback<PostsItem> =
                object : DiffUtil.ItemCallback<PostsItem>() {
                    override fun areItemsTheSame(oldItem: PostsItem, newItem: PostsItem): Boolean {
                        return oldItem.title == newItem.title
                    }

                    @SuppressLint("DiffUtilEquals")
                    override fun areContentsTheSame(oldItem: PostsItem, newItem: PostsItem): Boolean {
                        return oldItem == newItem
                    }
                }
        }
}