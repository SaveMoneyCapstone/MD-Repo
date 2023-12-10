package com.dicoding.savemoney.ui.fragment.add.income

import android.os.*
import android.view.*
import android.widget.*
import androidx.fragment.app.*
import com.dicoding.savemoney.databinding.*
import com.dicoding.savemoney.firebase.*

class IncomeFragment : Fragment() {
    private var _binding: FragmentIncomeBinding? = null
    private val binding get() = _binding!!

    private val firebaseIncomeManager: FirebaseIncomeManager = FirebaseIncomeManager()

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
    }

    private fun saveIncome() {
        val amount = binding.addEdTitle.text.toString().toDoubleOrNull()
        val category = binding.spCategory.selectedItem.toString()
        val note = binding.addEdDescription.text.toString()

        if (amount == null || category.isEmpty()) {
            Toast.makeText(requireContext(), "Please fill in all fields", Toast.LENGTH_SHORT).show()
            return
        }

        firebaseIncomeManager.saveIncome(amount, category, note) { success ->
            if (success) {
                Toast.makeText(requireContext(), "Income saved successfully", Toast.LENGTH_SHORT)
                    .show()
            } else {
                Toast.makeText(requireContext(), "Failed to save income", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
