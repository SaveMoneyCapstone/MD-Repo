package com.dicoding.savemoney.adapter

import android.view.*
import androidx.recyclerview.widget.*
import coil.*
import coil.transform.*
import com.dicoding.savemoney.data.response.*
import com.dicoding.savemoney.databinding.*

class CompanyProfileAdapter :
    ListAdapter<DataProfile, CompanyProfileAdapter.ProfileViewHolder>(DIFF_CALLBACK) {
    class ProfileViewHolder(
        private val binding: ItemProfileCompanyBinding,
    ) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(dataProfile: DataProfile) {
            binding.tvNameProfile.text = dataProfile.name
            binding.tvTickerProfile.text = dataProfile.symbol

            binding.ivLogoProfile.load(dataProfile.logo) {
                crossfade(true)
                transformations(CircleCropTransformation())

            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ProfileViewHolder {
        val binding =
            ItemProfileCompanyBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProfileViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProfileViewHolder, position: Int) {
        val profileCompany = getItem(position)
        holder.bind(profileCompany)
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<DataProfile>() {
            override fun areItemsTheSame(
                oldItem: DataProfile,
                newItem: DataProfile
            ): Boolean {
                return oldItem.symbol == newItem.symbol
            }

            override fun areContentsTheSame(
                oldItem: DataProfile,
                newItem: DataProfile
            ): Boolean {
                return oldItem == newItem
            }
        }
    }

}