package com.dicoding.savemoney.ui.detail

import android.os.*
import android.view.*
import android.widget.*
import androidx.appcompat.app.*
import androidx.fragment.app.*
import com.dicoding.savemoney.R
import com.dicoding.savemoney.data.model.*
import com.dicoding.savemoney.databinding.*
import com.dicoding.savemoney.firebase.*
import com.dicoding.savemoney.ui.fragment.add.expense.*
import com.dicoding.savemoney.ui.fragment.add.income.*
import com.dicoding.savemoney.utils.*
import com.google.firebase.auth.*
import java.text.*
import java.util.*

@Suppress("DEPRECATION")
class DetailTransactionActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailTransactionBinding
    private lateinit var transactionId: String
    private lateinit var firebaseTransactionManager: FirebaseTransactionManager
    private lateinit var firebaseExpenseManager: FirebaseExpenseManager
    private lateinit var firebaseIncomeManager: FirebaseIncomeManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailTransactionBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val userId = FirebaseAuth.getInstance().currentUser?.uid
        firebaseTransactionManager = FirebaseTransactionManager(userId ?: "")

        firebaseExpenseManager = FirebaseExpenseManager()
        firebaseIncomeManager = FirebaseIncomeManager()

        transactionId = intent.getStringExtra("TRANSACTION_ID") ?: ""

        if (transactionId.isNotEmpty()) {
            loadTransactionDetails()
        } else {
            // Handle the case where transactionId is empty
            finish()
        }
    }

    private fun loadTransactionDetails() {
        binding.progressBarDetail.visibility = View.GONE

        firebaseTransactionManager.loadExpenseTransactions { expenseTransactions ->
            firebaseTransactionManager.loadIncomeTransactions { incomeTransactions ->
                val allTransactions = mutableListOf<TransactionModel>().apply {
                    addAll(expenseTransactions)
                    addAll(incomeTransactions)
                }

                val selectedTransaction = allTransactions.find { it.id == transactionId }
                selectedTransaction?.let {
                    displayTransactionDetails(it)
                    setActionBarTitle(it.transactionType)
                } ?: run {
                    // Handle the case where the selected transaction is not found
                    finish()
                }
            }
        }
    }

    private fun displayTransactionDetails(transaction: TransactionModel) {
        val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        val formattedTimestamp = dateFormat.format(transaction.timestamp ?: Date())

        with(binding) {
            tvAmount.text = formatCurrencyTransaction(transaction.amount)
            tvCategory.text = transaction.category
            tvNote.text = transaction.note
            tvDate.text = formattedTimestamp
        }
    }

    private fun setActionBarTitle(transactionType: TransactionType?) {
        val title = when (transactionType) {
            TransactionType.INCOME -> getString(R.string.detail_pemasukan)
            TransactionType.EXPENSE -> getString(R.string.detail_pengeluaran)
            else -> {
                showToast("Unexpected transaction type for ID: $transactionId")
                finish()
                return
            }
        }
        supportActionBar?.title = title
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_detail, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_edit -> {
                openEditFragment()
                true
            }

            R.id.menu_delete -> {
                showDeleteConfirmationDialog()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun openEditFragment() {
        firebaseTransactionManager.getTransactionDetailsIncomeById(transactionId) { transactionIncome ->
            if (transactionIncome != null) {
                val editFragment: Fragment = IncomeFragment.newInstance(transactionId)
                hideContent()
                supportFragmentManager.beginTransaction()
                    .replace(android.R.id.content, editFragment)
                    .addToBackStack(null)
                    .commit()
            } else {
                firebaseTransactionManager.getTransactionDetailsExpenseById(transactionId) { transactionExpense ->
                    if (transactionExpense != null) {
                        val editFragment: Fragment = ExpenseFragment.newInstance(transactionId)
                        hideContent()
                        supportFragmentManager.beginTransaction()
                            .replace(android.R.id.content, editFragment)
                            .addToBackStack(null)
                            .commit()
                    } else {
                        // Handle the case where neither income nor expense is found
                        showContent()
                        showToast("Transaction details not found for ID: $transactionId")
                    }
                }
            }
        }
    }


    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        super.onBackPressed()
        showContent()
    }

    private fun hideContent() {
        binding.apply {
            tvAmount.visibility = View.GONE
            tvDate.visibility = View.GONE
            tvNote.visibility = View.GONE
            tvCategory.visibility = View.GONE

            ivAmount.visibility = View.GONE
            ivDate.visibility = View.GONE
            ivNote.visibility = View.GONE
            ivCategory.visibility = View.GONE
        }
    }

    private fun showContent() {
        binding.apply {
            tvAmount.visibility = View.VISIBLE
            tvDate.visibility = View.VISIBLE
            tvNote.visibility = View.VISIBLE
            tvCategory.visibility = View.VISIBLE

            ivAmount.visibility = View.VISIBLE
            ivDate.visibility = View.VISIBLE
            ivNote.visibility = View.VISIBLE
            ivCategory.visibility = View.VISIBLE
        }
    }

    private fun deleteTransaction() {
        firebaseIncomeManager.deleteTransactionIncome(transactionId) { incomeSuccess ->
            firebaseExpenseManager.deleteTransactionExpense(transactionId) { expenseSuccess ->
                if (incomeSuccess && expenseSuccess) {
                    showToast("Transaction deleted successfully")
                    finish()
                } else {
                    showToast("Failed to delete transaction")
                }
            }
        }
    }


    private fun showDeleteConfirmationDialog() {
        AlertDialog.Builder(this)
            .setTitle(getString(R.string.konfirmasi))
            .setMessage(getString(R.string.confirmation_delete))
            .setPositiveButton(getString(R.string.delete_transaction)) { _, _ ->
                deleteTransaction()
            }
            .setNegativeButton(getString(R.string.cancel), null)
            .show()
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        showContent()
        return true
    }
}
