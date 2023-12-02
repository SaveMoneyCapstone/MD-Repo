package com.dicoding.savemoney.ui.add

import android.os.*
import androidx.appcompat.app.*
import androidx.fragment.app.*
import androidx.viewpager2.adapter.*
import com.dicoding.savemoney.ui.fragment.add.expense.*
import com.dicoding.savemoney.ui.fragment.add.income.*

class SectionPagerAdapter internal constructor(activity: AppCompatActivity) :
    FragmentStateAdapter(activity) {
    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> {
                val fragment =  IncomeFragment()
                val bundle = Bundle()
                fragment.arguments = bundle
                fragment
            }

            1 -> {
                val fragment = ExpenseFragment()
                val bundle = Bundle()
                fragment.arguments = bundle
                fragment
            }

            else -> throw IllegalArgumentException("Invalid position: $position")
        }
    }
}