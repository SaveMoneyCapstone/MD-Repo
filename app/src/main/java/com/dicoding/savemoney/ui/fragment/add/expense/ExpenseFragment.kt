package com.dicoding.savemoney.ui.fragment.add.expense

import android.os.*
import android.view.*
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.fragment.app.*
import com.dicoding.savemoney.databinding.*
import com.dicoding.savemoney.firebase.*
import com.dicoding.savemoney.ui.add.AddTransactionActivity.Companion.dueDateMillis
import com.dicoding.savemoney.utils.DatePickerFragment

class ExpenseFragment : Fragment() {
    private var _binding: FragmentExpenseBinding? = null
    private val binding get() = _binding!!

    private val firebaseExpenseManager: FirebaseExpenseManager = FirebaseExpenseManager()


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
            Toast.makeText(requireContext(), "Please fill in all fields", Toast.LENGTH_SHORT).show()
            return
        }

        firebaseExpenseManager.saveExpense(1,amount, category, note, date) { success ->
            if (success) {
                Toast.makeText(requireContext(), "Income saved successfully", Toast.LENGTH_SHORT)
                    .show()
            } else {
                Toast.makeText(requireContext(), "Failed to save income", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }
}
