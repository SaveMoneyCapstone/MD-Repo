package com.dicoding.savemoney.ui.fragment.add.income

import android.os.*
import android.view.*
import androidx.fragment.app.*
import com.dicoding.savemoney.*

class IncomeFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_income, container, false)
    }
}