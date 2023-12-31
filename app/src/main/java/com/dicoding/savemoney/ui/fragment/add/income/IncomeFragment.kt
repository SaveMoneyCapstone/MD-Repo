package com.dicoding.savemoney.ui.fragment.add.income

import android.annotation.SuppressLint
import android.app.*
import android.content.Intent
import android.os.*
import android.view.*
import android.widget.*
import androidx.fragment.app.*
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.dicoding.savemoney.R
import com.dicoding.savemoney.data.model.TransactionModel
import com.dicoding.savemoney.databinding.FragmentIncomeBinding
import com.dicoding.savemoney.firebase.*
import com.dicoding.savemoney.ui.add.AddTransactionActivity.Companion.dueDateMillis
import com.dicoding.savemoney.ui.main.MainActivity
import com.dicoding.savemoney.utils.DatePickerFragment
import com.dicoding.savemoney.ui.detail.*
import com.google.firebase.auth.FirebaseAuth
import java.text.SimpleDateFormat
import java.util.*

@Suppress("DEPRECATION")
class IncomeFragment : Fragment() {
    private var _binding: FragmentIncomeBinding? = null
    private val binding get() = _binding!!

    private val firebaseIncomeManager: FirebaseIncomeManager = FirebaseIncomeManager()
    private lateinit var progressDialog: AlertDialog
    private lateinit var transactionId: String
    private lateinit var userId: String

    private var isEditing = false

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

        arguments?.let {
            transactionId = it.getString(TRANSACTION_ID, "") ?: ""
            if (transactionId.isNotEmpty()) {
                loadTransactionDetails()
            }
        }
    }

    private fun loadTransactionDetails() {
        userId = FirebaseAuth.getInstance().currentUser?.uid ?: ""
        val firebaseTransactionManager = FirebaseTransactionManager(userId)

        firebaseTransactionManager.getTransactionDetailsIncomeById(transactionId) { transaction ->
            if (transaction != null) {
                displayTransactionDetails(transaction)
                startEditing()
            } else {
                activity?.supportFragmentManager?.popBackStack()
            }
        }
    }

    private fun displayTransactionDetails(transaction: TransactionModel) {
        val dateFormat = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())
        val formattedTimestamp = dateFormat.format(transaction.date ?: Date())
        binding.addEdAmount.setText(transaction.amount.toString())
        binding.addEdDescription.setText(transaction.note)
        binding.addTvDueDate.setText(formattedTimestamp)
        binding.date.setOnClickListener {
            val dialogFragment = DatePickerFragment()
            dialogFragment.show(requireActivity().supportFragmentManager, "datePicker")
        }
    }

    @SuppressLint("UseRequireInsteadOfGet")
    private fun saveIncome() {
        val amount = binding.addEdAmount.text.toString().toDoubleOrNull()
        val category = binding.spCategory.selectedItem.toString()
        val note = binding.addEdDescription.text.toString()
        val dateText = binding.addTvDueDate.text.toString()
        val date = dueDateMillis

        if (amount == null || category.isEmpty() || dateText.isEmpty()) {
            Toast.makeText(
                requireContext(),
                R.string.please_fill_in_all_fields,
                Toast.LENGTH_SHORT
            ).show()
            return
        }

        isLoading(true)
        if (isEditing) {
            firebaseIncomeManager.updateTransactionIncome(
                userId,
                transactionId,
                amount,
                category,
                note,
                date
            ) { success ->
                isLoading(false)
                if (success) {
                    Toast.makeText(
                        requireContext(),
                        requireContext().getString(R.string.income_updated_successfully),
                        Toast.LENGTH_SHORT
                    ).show()
                    finishEditing()
                    _binding?.addEdAmount?.text?.clear()
                    _binding?.addEdDescription?.text?.clear()
                    activity?.supportFragmentManager?.popBackStack()
                    (activity as? DetailTransactionActivity)?.showContent()
                } else {
                    Toast.makeText(
                        requireContext(),
                        requireContext().getString(R.string.failed_to_update_income),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        } else {
            firebaseIncomeManager.saveIncome(amount, category, note, date) { success ->
                isLoading(false)
                if (success) {
                    Toast.makeText(
                        requireContext(),
                        getString(R.string.income_saved_successfully),
                        Toast.LENGTH_SHORT
                    ).show()
                    _binding?.addEdAmount?.text?.clear()
                    _binding?.addEdDescription?.text?.clear()
                    val intent = Intent(requireActivity(), MainActivity::class.java)
                    startActivity(intent)
                } else {
                    Toast.makeText(
                        requireContext(),
                        getString(R.string.failed_to_save_income),
                        Toast.LENGTH_SHORT
                    ).show()
                }
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

    private fun finishEditing() {
        isEditing = false
        _binding?.btnAddIncome?.text = getString(R.string.add_income)
    }

    private fun startEditing() {
        isEditing = true
        _binding?.btnAddIncome?.text = getString(R.string.save)
    }

    companion object {
        private const val TRANSACTION_ID = "TRANSACTION_ID"

        fun newInstance(transactionId: String): IncomeFragment {
            val fragment = IncomeFragment()
            val args = Bundle()
            args.putString(TRANSACTION_ID, transactionId)
            fragment.arguments = args
            return fragment
        }
    }
}
