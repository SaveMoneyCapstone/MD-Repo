package com.dicoding.savemoney.ui.fragment.statistic

import android.os.*
import android.view.*
import android.widget.*
import androidx.fragment.app.*
import androidx.lifecycle.*
import com.dicoding.savemoney.*


class StatisticFragment : Fragment() {
    private lateinit var statisticViewModel: StatisticViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        statisticViewModel =
            ViewModelProvider(this)[StatisticViewModel::class.java]
        val root = inflater.inflate(R.layout.fragment_statistic, container, false)
        val textView: TextView = root.findViewById(R.id.text_statistic)

        statisticViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }
        return root

    }
}