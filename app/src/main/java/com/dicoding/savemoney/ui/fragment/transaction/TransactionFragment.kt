package com.dicoding.savemoney.ui.fragment.transaction

import android.os.*
import android.view.*
import android.widget.*
import androidx.fragment.app.*
import androidx.lifecycle.*
import com.dicoding.savemoney.*

class TransactionFragment : Fragment() {
    private lateinit var transactionViewModel: TransactionViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        transactionViewModel =
            ViewModelProvider(this)[TransactionViewModel::class.java]
        val root = inflater.inflate(R.layout.fragment_transaction, container, false)


        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}