package com.dicoding.savemoney.ui.fragment.listbei

import android.os.*
import android.view.*
import android.widget.*
import androidx.fragment.app.*
import androidx.recyclerview.widget.*
import com.dicoding.savemoney.*
import com.dicoding.savemoney.adapter.*
import com.dicoding.savemoney.data.*
import com.dicoding.savemoney.databinding.*

class ListBeiFragment : Fragment() {

    private lateinit var stockAdapter: ListBeiAdapter
    private var _binding: FragmentListBeiBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentListBeiBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        stockAdapter = ListBeiAdapter()

        val viewModel: ListBeiViewModel by viewModels {
            ViewModelFactory.getInstance(requireActivity())
        }

        viewModel.beiCompanies.observe(viewLifecycleOwner) { result ->
            when (result) {
                is ResultState.Success -> {
                    isLoading(false)
                    val stockData = result.data.data?.results
                    if (stockData.isNullOrEmpty()) {
                        showNoDataMessage(getString(R.string.news_data_tidak_tersedia))
                    } else {
                        showNoDataMessage(null)
                        stockAdapter.submitList(stockData)

                    }
                }

                is ResultState.Error -> {
                    Toast.makeText(requireContext(), result.error, Toast.LENGTH_SHORT).show()
                    showNoDataMessage(getString(R.string.news_gagal_memuat_data))
                }

                is ResultState.Loading -> {
                    isLoading(true)
                    showNoDataMessage(getString(R.string.news_memuat_data))
                }
            }
        }

        binding.rvProduct.apply {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = stockAdapter
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
