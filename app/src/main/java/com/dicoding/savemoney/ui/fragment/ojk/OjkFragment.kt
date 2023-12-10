package com.dicoding.savemoney.ui.fragment.ojk

import android.os.*
import android.view.*
import android.widget.*
import androidx.fragment.app.*
import androidx.recyclerview.widget.*
import com.dicoding.savemoney.*
import com.dicoding.savemoney.adapter.*
import com.dicoding.savemoney.data.*
import com.dicoding.savemoney.data.response.*
import com.dicoding.savemoney.databinding.*


class OjkFragment : Fragment() {
    private lateinit var ojkInvestmentAdapter: OjkInvestmentAdapter

    private var _binding: FragmentOjkBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentOjkBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        ojkInvestmentAdapter = OjkInvestmentAdapter()

        val viewModel: OjkInvestmentViewModel by viewModels {
            ViewModelFactory.getInstance()
        }

        viewModel.ojkInvestmentData.observe(viewLifecycleOwner) { result ->
            when (result) {
                is ResultState.Loading -> {
                    isLoading(true)
                }

                is ResultState.Success -> {
                    isLoading(false)
                    val data = result.data.data?.apps
                    if (data != null) {
                        val dataList = data.map { appsItem ->
                            AppsItem(
                                owner = appsItem?.owner.orEmpty(),
                                name = appsItem?.name.orEmpty(),
                                url = appsItem?.url.orEmpty()
                            )
                        }
                        ojkInvestmentAdapter.submitList(dataList)
                    }
                }

                is ResultState.Error -> {
                    Toast.makeText(requireContext(), result.error, Toast.LENGTH_SHORT).show()
                    showNoDataMessage(getString(R.string.news_gagal_memuat_data))
                }

                else -> {}
            }
        }

        binding.rvOjk.apply {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = ojkInvestmentAdapter
        }

        viewModel.fetchOjkInvestment()

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