package com.dicoding.savemoney.ui.fragment.add.expense

import android.app.*
import android.content.Intent
import android.os.*
import android.view.*
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.fragment.app.*
import android.app.ProgressDialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.fragment.app.Fragment
import androidx.work.WorkManager
import com.dicoding.savemoney.R
import com.dicoding.savemoney.data.model.TransactionModel
import com.dicoding.savemoney.databinding.FragmentExpenseBinding
import com.dicoding.savemoney.firebase.*
import com.dicoding.savemoney.ui.add.AddTransactionActivity.Companion.dueDateMillis
import com.dicoding.savemoney.ui.main.MainActivity
import com.dicoding.savemoney.utils.DatePickerFragment
import com.dicoding.savemoney.firebase.FirebaseExpenseManager
import com.dicoding.savemoney.firebase.FirebaseTransactionManager
import com.dicoding.savemoney.ui.detail.*
import com.google.firebase.auth.FirebaseAuth

@Suppress("DEPRECATION")
class ExpenseFragment : Fragment() {
    private var _binding: FragmentExpenseBinding? = null
    private val binding get() = _binding!!
    private val firebaseDataManager: FirebaseDataManager = FirebaseDataManager()
    private val firebaseExpenseManager: FirebaseExpenseManager = FirebaseExpenseManager()
    private lateinit var progressDialog: ProgressDialog
    private var isEditing = false
    private lateinit var transactionId: String
    private lateinit var userId: String
    private lateinit var workManager: WorkManager



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

        arguments?.let {
            transactionId = it.getString(TRANSACTION_ID, "")
            if (transactionId.isNotEmpty()) {
                loadTransactionDetails()
            }
        }
        workManager = WorkManager.getInstance(requireContext())


    }

    private fun displayTransactionDetails(transaction: TransactionModel) {
        binding.addEdAmount.setText(transaction.amount.toString())
        binding.addEdDescription.setText(transaction.note)
    }

    private fun loadTransactionDetails() {
        userId = FirebaseAuth.getInstance().currentUser?.uid ?: ""
        val firebaseTransactionManager = FirebaseTransactionManager(userId)

        firebaseTransactionManager.getTransactionDetailsExpenseById(transactionId) { transaction ->
            if (transaction != null) {
                displayTransactionDetails(transaction)
                startEditing()
            } else {
                activity?.supportFragmentManager?.popBackStack()
            }
        }
    }


    @RequiresApi(Build.VERSION_CODES.O)
    private fun saveExpense() {
        val amount = binding.addEdAmount.text.toString().toDoubleOrNull()
        val category = binding.spCategory.selectedItem.toString()
        val note = binding.addEdDescription.text.toString()
        val dateText = binding.addTvDueDate.text.toString()
        val date = dueDateMillis
        if (amount == null || category.isEmpty() || dateText.isEmpty() ) {
            Toast.makeText(
                requireContext(),
                getString(R.string.please_fill_in_all_fields), Toast.LENGTH_SHORT
            ).show()
            return
        }

        isLoading(true)
        if (isEditing) {
            firebaseExpenseManager.updateTransactionExpense(
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
                        getString(R.string.expenses_updated_successfully), Toast.LENGTH_SHORT
                    )
                        .show()
                    finishEditing()
                    _binding?.addEdAmount?.text?.clear()
                    _binding?.addEdDescription?.text?.clear()
                    activity?.supportFragmentManager?.popBackStack()
                    (activity as? DetailTransactionActivity)?.showContent()
                } else {
                    Toast.makeText(
                        requireContext(),
                        getString(R.string.failed_to_update_expenses), Toast.LENGTH_SHORT
                    )
                        .show()
                }
            }
        } else {
            firebaseExpenseManager.saveExpense(amount, category, note, date) { success ->
                isLoading(false)
                if (success) {
                    Toast.makeText(
                        requireContext(),
                        getString(R.string.expense_saved_successfully), Toast.LENGTH_SHORT
                    )
                        .show()
                    _binding?.addEdAmount?.text?.clear()
                    _binding?.addEdDescription?.text?.clear()
                    setUpNotif()
                    val intent = Intent(requireActivity(), MainActivity::class.java)
                    startActivity(intent)
                } else {
                    Toast.makeText(
                        requireContext(),
                        getString(R.string.failed_to_save_expense), Toast.LENGTH_SHORT
                    )
                        .show()
                }
            }
        }
    }


    private fun setUpNotif() {

        firebaseDataManager.getHistoryMonth { _, _, _, avgExpense ->
            firebaseDataManager.getExpenseForToday { today ->
                val percentage = (today.toDouble()).div(avgExpense)*100

                if(percentage > 100) {
                    val notificationManager = requireActivity().getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
                    val builder = NotificationCompat.Builder(requireActivity(), CHANNEL_ID)
                        .setContentTitle("Expense Reminder")
                        .setSmallIcon(R.drawable.logo_app)
                        .setContentText("Your spending today is above the average daily spending this month")
                        .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                        .setBadgeIconType(R.drawable.logo_app)



                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        val channel = NotificationChannel(
                            CHANNEL_ID,
                            CHANNEL_NAME,
                            NotificationManager.IMPORTANCE_DEFAULT
                        )
                        builder.setChannelId(CHANNEL_ID)
                        notificationManager.createNotificationChannel(channel)
                    }
                    val notification = builder.build()
                    notificationManager.notify(NOTIFICATION_ID, notification)
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
        _binding?.btnAddExpense?.text = getString(R.string.add_expense)
    }

    private fun startEditing() {
        isEditing = true
        _binding?.btnAddExpense?.text = getString(R.string.save)
    }

    companion object {
        private const val TRANSACTION_ID = "TRANSACTION_ID"
        private const val NOTIFICATION_ID = 1
        private const val CHANNEL_ID = "channel_01"
        private const val CHANNEL_NAME = "savemoney channel"
        fun newInstance(transactionId: String): ExpenseFragment {
            val fragment = ExpenseFragment()
            val args = Bundle()
            args.putString(TRANSACTION_ID, transactionId)
            fragment.arguments = args
            return fragment
        }
    }
}
