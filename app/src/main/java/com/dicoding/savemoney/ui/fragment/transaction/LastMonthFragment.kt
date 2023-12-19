package com.dicoding.savemoney.ui.fragment.transaction

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.savemoney.adapter.MonthTransactionAdapter
import com.dicoding.savemoney.data.Transaction
import com.dicoding.savemoney.databinding.FragmentLastMonthBinding
import com.dicoding.savemoney.firebase.FirebaseDataManager
import com.dicoding.savemoney.utils.RupiahConverter
import com.google.firebase.auth.FirebaseAuth

class LastMonthFragment : Fragment() {
    private lateinit var firebaseDataManager: FirebaseDataManager
    private var _binding: FragmentLastMonthBinding? = null
    private lateinit var userList: ArrayList<Transaction>
    private val binding get() = _binding!!
    private lateinit var adapter: MonthTransactionAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentLastMonthBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = MonthTransactionAdapter()
        firebaseDataManager = FirebaseDataManager()

        val layoutManager = LinearLayoutManager(requireParentFragment().requireContext())
        binding.rvRecent.layoutManager = layoutManager
        userList = arrayListOf()
        val currentUser = FirebaseAuth.getInstance().currentUser
        currentUser?.let {
            setupFirebase()
        }
    }

    private fun setupFirebase() {
        firebaseDataManager.getLastMonth { list, incomes, expense, _ ->
            binding.rvRecent.setHasFixedSize(true)
            binding.rvRecent.adapter = adapter
            adapter.submitList(list)
            binding.balanceIncome.text = RupiahConverter.convertToRupiah(incomes)
            binding.balanceExpense.text = RupiahConverter.convertToRupiah(expense)
            binding.change.text = RupiahConverter.convertToRupiah((incomes.toInt() - expense.toInt()).toDouble())
        }
    }

}