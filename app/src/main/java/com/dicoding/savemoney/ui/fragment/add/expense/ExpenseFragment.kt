package com.dicoding.savemoney.ui.fragment.add.expense

import android.os.*
import android.view.*
import android.widget.*
import androidx.fragment.app.*
import com.dicoding.savemoney.databinding.*
import com.dicoding.savemoney.firebase.*

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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnAddExpense.setOnClickListener {
            saveExpense()
        }
    }

    private fun saveExpense() {
        val amount = binding.addEdAmount.text.toString().toDoubleOrNull()
        val category = binding.spCategory.selectedItem.toString()
        val note = binding.addEdDescription.text.toString()

        if (amount == null || category.isEmpty()) {
            Toast.makeText(requireContext(), "Please fill in all fields", Toast.LENGTH_SHORT).show()
            return
        }

        firebaseExpenseManager.saveExpense(amount, category, note) { success ->
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
