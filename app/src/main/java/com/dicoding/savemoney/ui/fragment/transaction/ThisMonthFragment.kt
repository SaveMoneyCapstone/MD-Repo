package com.dicoding.savemoney.ui.fragment.transaction

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.savemoney.R
import com.dicoding.savemoney.adapter.TransactionAdapter
import com.dicoding.savemoney.data.Transaction
import com.dicoding.savemoney.databinding.FragmentDashboardBinding
import com.dicoding.savemoney.databinding.FragmentThisMonthBinding
import com.dicoding.savemoney.firebase.FirebaseDataManager
import com.google.firebase.auth.FirebaseAuth

class ThisMonthFragment : Fragment() {
    private lateinit var firebaseDataManager: FirebaseDataManager
    private var _binding: FragmentThisMonthBinding? = null
    private lateinit var userList: ArrayList<Transaction>
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentThisMonthBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        val adapter = TransactionAdapter()
//        var layoutManager = LinearLayoutManager(requireActivity())
//        binding.rvRecent.layoutManager = layoutManager
        userList = arrayListOf()
        val currentUser = FirebaseAuth.getInstance().currentUser
//        currentUser?.let {
//            val userId = it.uid
//
//            firebaseDataManager.getHistoryMonth { list ->
//                binding.rvRecent.adapter = adapter
//                adapter.submitList(list)
//            }
//        }

    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
    }
}