package com.dicoding.savemoney.ui.add_expenditure

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.dicoding.savemoney.R
import com.dicoding.savemoney.databinding.ActivityAddExpenditureBinding
import com.dicoding.savemoney.databinding.FragmentAddExpensesBinding


class AddExpensesFragment : Fragment() {
    private val binding by lazy { FragmentAddExpensesBinding.inflate(layoutInflater)}

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }
    companion object {
    }
}