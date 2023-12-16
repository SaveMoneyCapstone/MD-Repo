package com.dicoding.savemoney.ui.fragment.dashboard

import android.annotation.*
import android.content.*
import android.graphics.*
import android.os.*
import android.util.*
import android.view.*
import android.widget.*
import androidx.activity.viewModels
import androidx.fragment.app.*
import androidx.lifecycle.*
import androidx.recyclerview.widget.*
import com.dicoding.savemoney.*
import com.dicoding.savemoney.R
import com.dicoding.savemoney.adapter.*
import com.dicoding.savemoney.data.*
import com.dicoding.savemoney.data.Transaction
import com.dicoding.savemoney.data.model.*
import com.dicoding.savemoney.databinding.*
import com.dicoding.savemoney.firebase.*
import com.dicoding.savemoney.ui.NewsActivity
import com.dicoding.savemoney.ui.add.*
import com.dicoding.savemoney.ui.fragment.sahamtrending.*
import com.dicoding.savemoney.ui.fragment.transaction.*
import com.dicoding.savemoney.ui.login.*
import com.dicoding.savemoney.ui.main.MainActivity
import com.dicoding.savemoney.ui.setting.*
import com.dicoding.savemoney.utils.*
import com.github.mikephil.charting.charts.*
import com.github.mikephil.charting.components.*
import com.github.mikephil.charting.data.*
import com.github.mikephil.charting.formatter.*
import com.github.mikephil.charting.utils.*
import com.google.android.material.floatingactionbutton.*
import com.google.firebase.auth.*
import com.google.firebase.firestore.*
import java.text.*
import java.util.*
import kotlin.collections.ArrayList

class DashboardFragment : Fragment() {
    private lateinit var lineChartManager: LineChartManager
    private var _binding: FragmentDashboardBinding? = null
    private var userList: MutableList<Transaction> = mutableListOf()
    private val expenseTransactionList: MutableList<TransactionModel> = mutableListOf()
    private val incomeTransactionList: MutableList<TransactionModel> = mutableListOf()
    private val binding get() = _binding!!
    private lateinit var adapterTransactionAdapter: TransactionAdapter
    private lateinit var adapterNewsAdapter: NewsAdapter

    private val viewModel by viewModels<DashboardViewModel> {
        ViewModelFactory.getInstance()
    }
    private lateinit var firebaseDataManager: FirebaseDataManager
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        firebaseDataManager = FirebaseDataManager()

        lineChartManager = LineChartManager(binding.chart)
        lineChartManager.setupLineChart()
        var layoutManager = LinearLayoutManager(requireActivity())
        binding.rvRecent.layoutManager = layoutManager

        adapterTransactionAdapter = TransactionAdapter()
        adapterNewsAdapter = NewsAdapter()
        userList = arrayListOf()
        // view data user to dashboard
        val currentUser = FirebaseAuth.getInstance().currentUser
        currentUser?.let {
            val userId = it.uid
            //get data name firestore to dashboard
            firebaseDataManager.getCurrentUserName(userId) { name ->
                binding.usernameUser.text = getString(R.string.welcome, name)
            }
            //get data balance firestore to dashboard
            firebaseDataManager.calculateBalance { balance ->
                binding.balance.text = getString(R.string.balance, balance)
            }
            //get data incomes firestore to dashboard
            firebaseDataManager.getIncomes { incomes ->
                binding.lotsOfIncome.text = getString(R.string.income, incomes)
            }
            //get data expense firestore to dashboard
            firebaseDataManager.getExpense { expense ->
                binding.lotsOfExpense.text = getString(R.string.expenses, expense)
            }

          firebaseDataManager.getHistory {
                binding.rvRecent.apply {
                    layoutManager = LinearLayoutManager(context)
                    setHasFixedSize(true)
                    adapter = adapterTransactionAdapter
                    adapterTransactionAdapter.submitList(it)

                }
            }
        }
        binding.viewMore.setOnClickListener {
            val intent = Intent(requireActivity(), NewsActivity::class.java)
            startActivity(intent)
        }

//        viewModel.getNews().observe(viewLifecycleOwner) {
//            viewModel.news.observe(viewLifecycleOwner) {
//                binding.rvNews.apply {
//                    layoutManager = LinearLayoutManager(context)
//                    setHasFixedSize(true)
//                    adapter = adapterNewsAdapter
//                    adapterNewsAdapter.submitList(it)
//                }
//            }
//        }
        showNews()
//        binding.rvRecent.apply {
//            layoutManager = LinearLayoutManager(context)
//            setHasFixedSize(true)
//            adapter = adapterTransactionAdapter
////            updateAdapterData()
//        }
        loadTransactionDataExpense()
        loadTransactionDataIncome()

        viewModel.isLoading().observe(viewLifecycleOwner) {
            showLoading(it)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    private fun showNews() {
        viewModel.getNews()
        viewModel.news.observe(viewLifecycleOwner) {
            listNews(it)
        }
    }
    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun listNews(news: List<PostsItem>) {
        val adapter = NewsAdapter()
        binding.rvNews.layoutManager = LinearLayoutManager(context)
        binding.rvNews.setHasFixedSize(true)

        adapter.submitList(news.take(3))
        binding.rvNews.adapter =adapter
    }

    private fun loadTransactionDataExpense() {
        val currentUser = FirebaseAuth.getInstance().currentUser
        currentUser?.let { user ->
            val userId = user.uid
            val firebaseTransactionManager = FirebaseTransactionManager(userId)
            firebaseTransactionManager.loadExpenseTransactions { transactions ->
                activity?.runOnUiThread {
                    expenseTransactionList.clear()
                    expenseTransactionList.addAll(transactions.map {
                        it.copy(transactionType = TransactionType.EXPENSE)
                    })
//                    updateAdapterData()
                }
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun loadTransactionDataIncome() {
        val currentUser = FirebaseAuth.getInstance().currentUser
        currentUser?.let { user ->
            val userId = user.uid
            val firebaseTransactionManager = FirebaseTransactionManager(userId)
            firebaseTransactionManager.loadIncomeTransactions { transactions ->
                activity?.runOnUiThread {
                    incomeTransactionList.clear()
                    incomeTransactionList.addAll(transactions.map {
                        it.copy(transactionType = TransactionType.INCOME)
                    })
//                    updateAdapterData()
                }
            }
        }
    }

//    private fun updateAdapterData() {
//        val combinedList = mutableListOf<TransactionModel>()
//        combinedList.addAll(incomeTransactionList)
//        combinedList.addAll(expenseTransactionList)
//        combinedList.sortByDescending { it.date }
//        adapterTransactionAdapter.submitList(combinedList)
//    }
}
