package com.dicoding.savemoney.ui.add_expenditure

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.dicoding.savemoney.R
import com.dicoding.savemoney.data.UserData
import com.dicoding.savemoney.databinding.FragmentAddExpensesBinding
import com.dicoding.savemoney.databinding.FragmentAddIncomeBinding
import com.dicoding.savemoney.ui.ViewModelFactory
import com.dicoding.savemoney.ui.add.AddExpenseViewModel



class AddIncomeFragment : Fragment() {
    private var dueDateMillis: Long = System.currentTimeMillis()
    private val binding by lazy { FragmentAddIncomeBinding.inflate(layoutInflater)}
    private val viewModel: AddExpenseViewModel by activityViewModels() {
        ViewModelFactory.getInstance(requireContext().applicationContext)
    }




    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.addIncomeBtn.setOnClickListener {
            val amount = binding.addEdAmount.text.toString().toLong()
            val notes = binding.addEdNotes.text.toString()
            val category = binding.category.selectedItemPosition
            val data = UserData(0, amount, notes, category, dueDateMillis)
            viewModel.saveData(data)

        }
    }
    companion object {
    }

}