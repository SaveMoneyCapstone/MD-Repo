package com.dicoding.savemoney.ui.fragment.dashboard

import com.dicoding.savemoney.utils.LineChartManager
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
import com.dicoding.savemoney.R
import com.dicoding.savemoney.adapter.*
import com.dicoding.savemoney.data.*
import com.dicoding.savemoney.data.Transaction
import com.dicoding.savemoney.databinding.*
import com.dicoding.savemoney.firebase.*
import com.dicoding.savemoney.ui.add.*
import com.dicoding.savemoney.ui.fragment.sahamtrending.*
import com.dicoding.savemoney.ui.login.*
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
    private val binding get() = _binding!!

//    private lateinit var mAdapter: FirestoreRecyclerAdapter<UserData, TransactionAdapter.MyViewHolder>
//    private val mFirestore = FirebaseFirestore.getInstance()
//    private val mUsersCollection = mFirestore.collection(PATH_COLLECTION)
//    private val mQuery = mUsersCollection.orderBy(TIME_STAMP, Query.Direction.ASCENDING)

    private lateinit var userList: ArrayList<Transaction>
    private lateinit var firebaseDataManager: FirebaseDataManager
    private lateinit var adapter : TransactionAdapter
    private val firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()
    private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()

    @SuppressLint("MissingInflatedId")
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

        //example line chart
        lineChartManager = LineChartManager(binding.chart)
        lineChartManager.setupLineChart()
        val adapter = TransactionAdapter()
        var layoutManager = LinearLayoutManager(requireActivity())
        binding.rvRecent.layoutManager = layoutManager

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
                binding.balance.text = getString(R.string.balance, balance.toString())
            }
            //get data incomes firestore to dashboard
            firebaseDataManager.getIncomes { incomes ->
                binding.lotsOfIncome.text = getString(R.string.income, incomes.toString())
            }
            //get data expense firestore to dashboard
            firebaseDataManager.getExpense { expense ->
                binding.lotsOfExpense.text = getString(R.string.expenses, expense.toString())
            }


           firebaseDataManager.getHistory { list ->
               binding.rvRecent.adapter = adapter
               adapter.submitList(list)
            }
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
