package com.dicoding.savemoney.ui.fragment.transaction

import android.os.*
import android.view.*
import android.widget.*
import androidx.annotation.StringRes
import androidx.fragment.app.*
import androidx.lifecycle.*
import com.dicoding.savemoney.*
import com.dicoding.savemoney.databinding.FragmentExpenseBinding
import com.dicoding.savemoney.databinding.FragmentIncomeBinding
import com.dicoding.savemoney.databinding.FragmentTransactionBinding
import com.dicoding.savemoney.ui.add.AddTransactionActivity
import com.dicoding.savemoney.ui.add.SectionPagerAdapter
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class TransactionFragment : Fragment() {
    private var _binding: FragmentTransactionBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTransactionBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val sectionPagerAdapter = TxSectionPagerAdapter(this)
        binding.viewPager.adapter = sectionPagerAdapter
        TabLayoutMediator(
            binding.tabs, binding.viewPager
        ) { tab: TabLayout.Tab, position: Int ->
            tab.text = resources.getString(TransactionFragment.TAB_TITLES[position])
        }.attach()
    }

    companion object {
        @StringRes
        private val TAB_TITLES = intArrayOf(R.string.last_month, R.string.this_month)

    }
}