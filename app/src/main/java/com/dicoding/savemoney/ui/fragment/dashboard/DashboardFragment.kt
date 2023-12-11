package com.dicoding.savemoney.ui.fragment.dashboard

import android.annotation.*
import android.content.*
import android.graphics.*
import android.os.*
import android.view.*
import android.widget.*
import androidx.fragment.app.*
import androidx.lifecycle.*
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.savemoney.*
import com.dicoding.savemoney.databinding.FragmentDashboardBinding
import com.dicoding.savemoney.ui.ViewModelFactory
import com.dicoding.savemoney.ui.adapter.RecentTransactionsAdapter
import com.dicoding.savemoney.ui.add.*
import com.dicoding.savemoney.ui.fragment.transaction.TransactionsActivity
import com.dicoding.savemoney.ui.login.*
import com.github.mikephil.charting.charts.*
import com.github.mikephil.charting.components.*
import com.github.mikephil.charting.data.*
import com.github.mikephil.charting.formatter.*
import com.github.mikephil.charting.utils.*
import com.google.android.material.floatingactionbutton.*
import java.text.*
import java.util.*

@Suppress("DEPRECATION")
class DashboardFragment : Fragment() {
    private val viewModel: DashboardViewModel by activityViewModels() {
        ViewModelFactory.getInstance(requireContext().applicationContext)
    }
    private var _binding: FragmentDashboardBinding? = null
    private val binding get() = _binding!!

    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val layoutManager = LinearLayoutManager(requireActivity())
        binding.rvRecent.layoutManager = layoutManager
        viewModel.getData().observe(viewLifecycleOwner) {
            val adapter = RecentTransactionsAdapter()
            binding.rvRecent.adapter = adapter
            adapter.submitList(it)
        }

        viewModel.getExpenses().observe(viewLifecycleOwner) {
            binding.lotsOfExpensess.text = RupiahConverter.convertToRupiah(it.toLong())
        }

        viewModel.getIncome().observe(viewLifecycleOwner) {
            binding.lotsOfIncome.text = RupiahConverter.convertToRupiah(it.toLong())
        }

        viewModel.getExpenses().observe(viewLifecycleOwner) {expenses ->
            viewModel.getIncome().observe(viewLifecycleOwner) {
                val balance = it-expenses
                binding.balance.text = RupiahConverter.convertToRupiah(balance.toLong())
            }
        }

        binding.viewMore.setOnClickListener {
            val intent = Intent(requireActivity(), TransactionsActivity::class.java)
            startActivity(intent)
        }


        val fab: FloatingActionButton = binding.fab
        fab.setOnClickListener {
            val intent = Intent(requireActivity(), AddExpenseActivity::class.java)
            startActivity(intent)
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.appbar_dashboard_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    @Deprecated("Deprecated in Java")
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_notification -> {
                Toast.makeText(requireContext(), "Notification Menu Clicked", Toast.LENGTH_SHORT)
                    .show()
                true
            }

            R.id.menu_profile -> {
                Toast.makeText(requireContext(), "Profile Menu Clicked", Toast.LENGTH_SHORT).show()
                true
            }

            R.id.logout -> {
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }
}
