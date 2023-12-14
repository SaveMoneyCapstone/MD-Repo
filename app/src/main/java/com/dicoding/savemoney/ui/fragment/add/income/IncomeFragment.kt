package com.dicoding.savemoney.ui.fragment.add.income

import android.annotation.*
import android.app.*
import android.content.Intent
import android.os.*
import android.view.*
import android.widget.*
import androidx.fragment.app.*
import androidx.fragment.app.Fragment
import com.dicoding.savemoney.R
import com.dicoding.savemoney.databinding.*
import com.dicoding.savemoney.firebase.*
import com.dicoding.savemoney.ui.add.AddTransactionActivity.Companion.dueDateMillis
import com.dicoding.savemoney.ui.fragment.dashboard.DashboardFragment
import com.dicoding.savemoney.ui.main.MainActivity
import com.dicoding.savemoney.utils.DatePickerFragment

class IncomeFragment : Fragment() {
    private var _binding: FragmentIncomeBinding? = null
    private val binding get() = _binding!!

    private val firebaseIncomeManager: FirebaseIncomeManager = FirebaseIncomeManager()
    private lateinit var progressDialog: ProgressDialog

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentIncomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnAddIncome.setOnClickListener {
            saveIncome()
        }
        binding.date.setOnClickListener {
            val dialogFragment = DatePickerFragment()
            dialogFragment.show(requireActivity().supportFragmentManager, "datePicker")
        }
    }

    private fun saveIncome() {
        val amount = binding.addEdAmount.text.toString().toDoubleOrNull()
        val category = binding.spCategory.selectedItem.toString()
        val note = binding.addEdDescription.text.toString()
        val date = dueDateMillis

        if (amount == null || category.isEmpty()) {
            Toast.makeText(requireContext(), R.string.please_fill_in_all_fields, Toast.LENGTH_SHORT).show()
            return
        }
        isLoading(true)

        firebaseIncomeManager.saveIncome(amount, category, note,date) { success ->
            if (success) {
                isLoading(false)
                Toast.makeText(requireContext(),
                    getString(R.string.income_saved_successfully), Toast.LENGTH_SHORT)
                    .show()
                binding.addEdAmount.text?.clear()
                binding.addEdDescription.text?.clear()
                val intent = Intent(requireActivity(), MainActivity::class.java)
                startActivity(intent)


            } else {
                isLoading(false)
                Toast.makeText(requireContext(),
                    getString(R.string.failed_to_save_income), Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun isLoading(isLoading: Boolean) {
        if (isLoading) {
            progressDialog = ProgressDialog(requireContext())
            progressDialog.setMessage("Loading...")
            progressDialog.show()
        } else {
            progressDialog.dismiss()
        }
    }
}
