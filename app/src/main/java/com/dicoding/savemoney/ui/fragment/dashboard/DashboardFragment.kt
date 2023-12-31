package com.dicoding.savemoney.ui.fragment.dashboard

import android.annotation.*
import android.content.*
import android.graphics.*
import android.os.*
import android.util.*
import android.view.*
import android.widget.*
import androidx.fragment.app.*
import androidx.lifecycle.*
import androidx.recyclerview.widget.*
import com.dicoding.savemoney.*
import com.dicoding.savemoney.R
import com.dicoding.savemoney.adapter.*
import com.dicoding.savemoney.data.*
import com.dicoding.savemoney.data.model.*
import com.dicoding.savemoney.data.preference.UserSessionManager
import com.dicoding.savemoney.data.response.UserData
import com.dicoding.savemoney.data.response.UserPredict
import com.dicoding.savemoney.databinding.*
import com.dicoding.savemoney.databinding.FragmentDashboardBinding
import com.dicoding.savemoney.firebase.*
import com.dicoding.savemoney.ui.NewsActivity
import com.dicoding.savemoney.ui.add.*
import com.dicoding.savemoney.ui.fragment.sahamtrending.*
import com.dicoding.savemoney.ui.fragment.transaction.*
import com.dicoding.savemoney.ui.login.*
import com.dicoding.savemoney.ui.setting.*
import com.dicoding.savemoney.ui.splash.SplashScreenActivity
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

@Suppress("SameParameterValue")
class DashboardFragment : Fragment() {
    private lateinit var lineChartManager: LineChartManager
    private val binding by lazy { FragmentDashboardBinding.inflate(layoutInflater) }
    private lateinit var adapterTransactionAdapter: TransactionAdapter
    private lateinit var adapterNewsAdapter: NewsAdapter
    private lateinit var userSessionManager: UserSessionManager
    private val viewModel by viewModels<DashboardViewModel> {
        ViewModelFactory.getInstance()
    }
    private lateinit var firebaseDataManager: FirebaseDataManager


    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val _binding = binding
        return _binding.root
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        binding.refreshLayout.setOnRefreshListener {
            loadDataCard()
            showToast("The data has been successfully updated")
            binding.refreshLayout.isRefreshing = false
        }

        userSessionManager = UserSessionManager(requireContext())
        if (!userSessionManager.isUserLoggedIn()) {
            val intent = Intent(requireActivity(), SplashScreenActivity::class.java)
            startActivity(intent)
        }

        firebaseDataManager = FirebaseDataManager()

        lineChartManager = LineChartManager(binding.chart)
        lineChartManager.setupLineChart()
        val layoutManager = LinearLayoutManager(requireActivity())
        binding.rvRecent.layoutManager = layoutManager
        loadDataCard()

        binding.viewMore.setOnClickListener {
            val intent = Intent(requireActivity(), NewsActivity::class.java)
            startActivity(intent)
        }

        showNews()

        viewModel.isLoading().observe(viewLifecycleOwner) {
            showLoading(it)
        }
    }
    private fun loadDataCard() {
        adapterTransactionAdapter = TransactionAdapter(requireContext())
        adapterNewsAdapter = NewsAdapter()
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

            firebaseDataManager.fetchData { income, expense ->
                val userData = UserPredict(expense)
                viewModel.fetchPredictRecom(userData).observe(viewLifecycleOwner) { result ->
                    when (result) {
                        is ResultState.Loading -> {
                            
                        }

                        is ResultState.Success -> {
                            binding.predict.text = RupiahConverter.convertToRupiah(result.data.data.prediksiPengeluaranBesok.toDouble())
                            binding.recommendation.text = RupiahConverter.convertToRupiah(result.data.data.rekomendasiPengeluaran.toDouble())
                        }

                        is ResultState.Error -> {
                        }
                    }
                }

            }

            firebaseDataManager.getHistory {
                binding.rvRecent.apply {
                    layoutManager = LinearLayoutManager(context)
                    setHasFixedSize(true)
                    adapter = adapterTransactionAdapter
                    adapterTransactionAdapter.submitList(it)
                }
            }
            firebaseDataManager.getHistoryMonth { _, _, _, avgExpense ->
                firebaseDataManager.getExpenseForToday { today ->
                    with(binding) {
                        todayExpense.text = RupiahConverter.convertToRupiah(today.toDouble())
                        val percentage = (today.toDouble()).div(avgExpense)*100
                        if(percentage > 100) {
                            percent.setTextColor(Color.RED)
                        } else {
                            percent.setTextColor(Color.GREEN)
                        }
                        percent.text = getString(R.string.percent_from_month_average, "%.2f".format(percentage) + "%")
                    }

                }
            }

        }
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
    private fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    @Deprecated("Deprecated in Java")
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.appbar_dashboard_menu, menu)
        return super.onCreateOptionsMenu(menu,inflater)
    }

    @Deprecated("Deprecated in Java")
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {

            R.id.menu_profile -> {
                Toast.makeText(requireActivity(), "Profile Menu Clicked", Toast.LENGTH_SHORT).show()
                true
            }

            R.id.setting -> {
                val intent = Intent(requireActivity(), SettingsActivity::class.java)
                startActivity(intent)
                true
            }

            R.id.logout -> {
                userSessionManager.logoutUser()
                val intent = Intent(requireActivity(), LoginActivity::class.java)
                startActivity(intent)
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }
}