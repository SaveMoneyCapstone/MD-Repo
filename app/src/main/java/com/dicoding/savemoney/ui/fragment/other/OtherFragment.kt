package com.dicoding.savemoney.ui.fragment.other

import android.os.*
import android.view.*
import android.widget.*
import androidx.appcompat.app.*
import androidx.fragment.app.*
import androidx.lifecycle.*
import com.dicoding.savemoney.*


class OtherFragment : Fragment() {
    private lateinit var otherViewModel: OtherViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        otherViewModel =
            ViewModelProvider(this)[OtherViewModel::class.java]
        val root = inflater.inflate(R.layout.fragment_other, container, false)
        val textView: TextView = root.findViewById(R.id.text_other)

        otherViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }
        return root
    }
}