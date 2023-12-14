package com.dicoding.savemoney.ui.fragment.add.expense

import android.app.*
import android.content.Intent
import android.os.*
import android.view.*
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.fragment.app.*
import androidx.fragment.app.Fragment
import com.dicoding.savemoney.R
import com.dicoding.savemoney.databinding.*
import com.dicoding.savemoney.databinding.FragmentExpenseBinding
import com.dicoding.savemoney.firebase.*
import com.dicoding.savemoney.ui.add.AddTransactionActivity.Companion.dueDateMillis
import com.dicoding.savemoney.ui.fragment.dashboard.DashboardFragment
import com.dicoding.savemoney.ui.main.MainActivity
import com.dicoding.savemoney.utils.DatePickerFragment

class ExpenseFragment : Fragment() {
    private var _binding: FragmentExpenseBinding? = null
    private val binding get() = _binding!!

    private val firebaseExpenseManager: FirebaseExpenseManager = FirebaseExpenseManager()
    private lateinit var progressDialog: ProgressDialog



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentExpenseBinding.inflate(inflater, container, false)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.date.setOnClickListener {
            val dialogFragment = DatePickerFragment()
            dialogFragment.show(requireActivity().supportFragmentManager, "datePicker")
        }
        binding.btnAddExpense.setOnClickListener {
            saveExpense()
        }
    }


    @RequiresApi(Build.VERSION_CODES.O)
    private fun saveExpense() {
        val amount = binding.addEdAmount.text.toString().toDoubleOrNull()
        val category = binding.spCategory.selectedItem.toString()
        val note = binding.addEdDescription.text.toString()
        val date = dueDateMillis
        if (amount == null || category.isEmpty()) {
            Toast.makeText(requireContext(),
                getString(R.string.please_fill_in_all_fields), Toast.LENGTH_SHORT).show()
            return
        }
        isLoading(true)


        firebaseExpenseManager.saveExpense(amount, category, note, date) { success ->
            isLoading(false)
            if (success) {
                Toast.makeText(requireContext(),
                    getString(R.string.expense_saved_successfully), Toast.LENGTH_SHORT)
                    .show()
                binding.addEdAmount.text?.clear()
                binding.addEdDescription.text?.clear()
                val intent = Intent(requireActivity(), MainActivity::class.java)
                startActivity(intent)
            } else {
                Toast.makeText(requireContext(),
                    getString(R.string.failed_to_save_expense), Toast.LENGTH_SHORT)
                    .show()
            }
        }
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
