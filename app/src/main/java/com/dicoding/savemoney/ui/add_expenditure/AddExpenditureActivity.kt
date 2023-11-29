package com.dicoding.savemoney.ui.add_expenditure

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.annotation.StringRes
import androidx.viewpager2.widget.ViewPager2
import com.dicoding.savemoney.R
import com.dicoding.savemoney.databinding.ActivityAddExpenditureBinding
import com.dicoding.savemoney.ui.adapter.AddSectionPagerAdapter
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class AddExpenditureActivity : AppCompatActivity() {
//    private val viewModel by viewModels<AddExpenditureViewModel> {
//        ViewModelFactory.getInstance(this)
//    }

    private val binding by lazy { ActivityAddExpenditureBinding.inflate(layoutInflater)}
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

    val sectionPagerAdapter = AddSectionPagerAdapter(this)

    val viewPager: ViewPager2 = binding.viewPager
        viewPager.adapter = sectionPagerAdapter
        val tabs: TabLayout = binding.tab
        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()

    }
    companion object {
        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.incomes_tab,
            R.string.expenses_tab
        )
    }
}