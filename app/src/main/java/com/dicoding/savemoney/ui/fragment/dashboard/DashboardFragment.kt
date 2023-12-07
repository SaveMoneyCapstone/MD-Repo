package com.dicoding.savemoney.ui.fragment.dashboard

import android.annotation.*
import android.content.*
import android.graphics.*
import android.os.*
import android.view.*
import android.widget.*
import androidx.fragment.app.*
import androidx.lifecycle.*
import androidx.recyclerview.widget.*
import com.dicoding.savemoney.*
import com.dicoding.savemoney.adapter.*
import com.dicoding.savemoney.data.*
import com.dicoding.savemoney.databinding.*
import com.dicoding.savemoney.ui.add.*
import com.dicoding.savemoney.ui.fragment.listbei.*
import com.dicoding.savemoney.ui.login.*
import com.dicoding.savemoney.ui.setting.*
import com.dicoding.savemoney.utils.*
import com.github.mikephil.charting.charts.*
import com.github.mikephil.charting.components.*
import com.github.mikephil.charting.data.*
import com.github.mikephil.charting.formatter.*
import com.github.mikephil.charting.utils.*
import com.google.android.material.floatingactionbutton.*
import java.text.*
import java.util.*

@Suppress("KotlinConstantConditions")
class DashboardFragment : Fragment() {
    private lateinit var lineChartManager: LineChartManager
    private lateinit var companyProfileAdapter: CompanyProfileAdapter
    private var _binding: FragmentDashboardBinding? = null
    private val binding get() = _binding!!

    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val viewModel: CompanyProfileViewModel by viewModels {
            ViewModelFactory.getInstance()
        }

        //example line chart
        lineChartManager = LineChartManager(binding.chart)
        lineChartManager.setupLineChart()

        companyProfileAdapter = CompanyProfileAdapter()

        viewModel.profileCompany.observe(viewLifecycleOwner) { result ->
            when (result) {
                is ResultState.Loading -> {
                    isLoading(true)
                    showNoDataMessage(getString(R.string.news_memuat_data))
                }

                is ResultState.Success -> {
                    isLoading(false)
                    val data = result.data.data
                    if (data != null) {
                        showNoDataMessage(getString(R.string.news_data_tidak_tersedia))
                        showNoDataMessage(null)
                    } else {
                        companyProfileAdapter.submitList(listOf(data))
                    }
                }

                is ResultState.Error -> {
                    Toast.makeText(requireContext(), result.error, Toast.LENGTH_SHORT).show()
                    showNoDataMessage(getString(R.string.news_gagal_memuat_data))
                }
            }
        }

        viewModel.fetchCompanyProfile()

        binding.rvCompanyProfile.apply {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = companyProfileAdapter
        }
    }

    private fun isLoading(isLoading: Boolean) {
        binding.progresBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun showNoDataMessage(message: String?) {
        binding.tvMessage.apply {
            visibility = if (!message.isNullOrBlank()) View.VISIBLE else View.GONE
            text = message
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
