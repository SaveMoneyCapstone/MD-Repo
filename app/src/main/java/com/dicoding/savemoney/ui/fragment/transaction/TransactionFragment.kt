package com.dicoding.savemoney.ui.fragment.transaction

import android.os.*
import android.view.*
import android.widget.*
import androidx.fragment.app.*
import androidx.lifecycle.*
import com.dicoding.savemoney.*

class TransactionFragment : Fragment() {
    private lateinit var transactionViewModel: TransactionViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        transactionViewModel =
            ViewModelProvider(this)[TransactionViewModel::class.java]
        val root = inflater.inflate(R.layout.fragment_transaction, container, false)
        val textView: TextView = root.findViewById(R.id.text_transaction)

        transactionViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }
        return root
    }
}