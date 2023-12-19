package com.dicoding.savemoney.ui.fragment.transaction

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
class TxSectionPagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> {
                val fragment = LastMonthFragment()
                val bundle = Bundle()
                fragment.arguments = bundle
                fragment
            }

            1 -> {
                val fragment = ThisMonthFragment()
                val bundle = Bundle()
                fragment.arguments = bundle
                fragment
            }

            else -> throw IllegalArgumentException("Invalid position: $position")
        }
    }
}