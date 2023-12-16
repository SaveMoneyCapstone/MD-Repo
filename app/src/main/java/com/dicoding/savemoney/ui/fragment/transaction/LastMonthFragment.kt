package com.dicoding.savemoney.ui.fragment.transaction

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.savemoney.R
import com.dicoding.savemoney.adapter.MonthTransactionAdapter
import com.dicoding.savemoney.data.Transaction
import com.dicoding.savemoney.databinding.FragmentLastMonthBinding
import com.dicoding.savemoney.databinding.FragmentThisMonthBinding
import com.dicoding.savemoney.firebase.FirebaseDataManager
import com.dicoding.savemoney.utils.RupiahConverter
import com.google.firebase.auth.FirebaseAuth
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


class LastMonthFragment : Fragment() {
    private lateinit var firebaseDataManager: FirebaseDataManager
    private var _binding: FragmentLastMonthBinding? = null
    private lateinit var userList: ArrayList<Transaction>
    private val binding get() = _binding!!
    private lateinit var adapter: MonthTransactionAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentLastMonthBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = MonthTransactionAdapter()
        firebaseDataManager = FirebaseDataManager()

        var layoutManager = LinearLayoutManager(requireParentFragment().requireContext())
        binding.rvRecent.layoutManager = layoutManager
        userList = arrayListOf()
        val currentUser = FirebaseAuth.getInstance().currentUser
        currentUser?.let {
            val userId = it.uid
            setupFirebase()
        }
    }

    private fun setupFirebase() {
        val calendar = Calendar.getInstance()
        val month = calendar.get(Calendar.MONTH)
        val year = calendar.get(Calendar.YEAR)
        val first= "$year-$month-01"
        var date: Date
        val formatter = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        date = formatter.parse(first)
        var firstDateMillis: Long = System.currentTimeMillis()
        var endDateMillis: Long = System.currentTimeMillis()
        firstDateMillis = date.time
        val end = "$year-$month-31"
        var endDate: Date
        val formatterend = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        endDate = formatterend.parse(end)
        endDateMillis = endDate.time
        firebaseDataManager.getHistoryMonth(firstDateMillis/1000,endDateMillis/1000) { list, incomes, expense ->
            binding.rvRecent.setHasFixedSize(true)
            binding.rvRecent.adapter = adapter
            adapter.submitList(list)
            binding.balanceIncome.text = RupiahConverter.convertToRupiah(incomes)
            binding.balanceExpense.text = RupiahConverter.convertToRupiah(expense)
            binding.change.text = RupiahConverter.convertToRupiah((incomes.toInt() - expense.toInt()).toDouble())
        }
    }

    companion object {

    }
}