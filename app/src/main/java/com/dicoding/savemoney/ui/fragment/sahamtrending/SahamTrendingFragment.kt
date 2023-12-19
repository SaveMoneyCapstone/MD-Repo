package com.dicoding.savemoney.ui.fragment.sahamtrending

import android.os.*
import android.view.*
import androidx.fragment.app.*
import androidx.recyclerview.widget.*
import com.dicoding.savemoney.*
import com.dicoding.savemoney.adapter.*
import com.dicoding.savemoney.data.*
import com.dicoding.savemoney.data.response.UserData
import com.dicoding.savemoney.databinding.*
import com.dicoding.savemoney.firebase.FirebaseDataManager
import com.google.firebase.auth.FirebaseAuth
class SahamTrendingFragment : Fragment() {
    private lateinit var sahamTrendingAdapter: SahamTrendingAdapter
    private var _binding: FragmentSahamTrendingBinding? = null
    private val binding get() = _binding!!
    private lateinit var firebaseDataManager: FirebaseDataManager
    private val viewModel: SahamTrendingViewModel by viewModels {
        ViewModelFactory.getInstance()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSahamTrendingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sahamTrendingAdapter = SahamTrendingAdapter()
        firebaseDataManager = FirebaseDataManager()
        val currentUser = FirebaseAuth.getInstance().currentUser
        currentUser?.let {
            firebaseDataManager.fetchData {incomes, expense ->
                val userData = UserData(expense,incomes)
                viewModel.fetchStockResponse(userData).observe(viewLifecycleOwner) { result ->
                        when (result) {
                            is ResultState.Loading -> {
                                isLoading(true)
                            }

                            is ResultState.Success -> {
                                isLoading(false)
                                showNoDataMessage(null)
                                sahamTrendingAdapter.submitList(result.data.data.recomendations)
                            }

                            is ResultState.Error -> {
                                isLoading(false)
                                showNoDataMessage("There are no stock recommendations at this time.")
                            }
                        }
                    }



            }
        }


        binding.rvSahamTrending.apply {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = sahamTrendingAdapter
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
}
