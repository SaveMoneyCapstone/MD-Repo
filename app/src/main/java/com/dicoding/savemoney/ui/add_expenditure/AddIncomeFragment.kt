package com.dicoding.savemoney.ui.add_expenditure

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.activityViewModels
import com.dicoding.savemoney.R
import com.dicoding.savemoney.data.UserData
import com.dicoding.savemoney.data.UserIncome
import com.dicoding.savemoney.databinding.FragmentAddExpensesBinding
import com.dicoding.savemoney.databinding.FragmentAddIncomeBinding
import com.dicoding.savemoney.ExpensesCategory.Companion.getByNumberIncome
import com.dicoding.savemoney.ui.ViewModelFactory
import com.dicoding.savemoney.ui.add.AddExpenseActivity.Companion.dueDateMillis
import com.dicoding.savemoney.ui.add.AddExpenseViewModel
import com.dicoding.savemoney.ui.fragment.dashboard.DashboardFragment
import com.dicoding.savemoney.ui.main.MainActivity
import com.dicoding.savemoney.utils.DatePickerFragment


class AddIncomeFragment : Fragment() {
    //private var dueDateMillis: Long = System.currentTimeMillis()
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

        binding.date.setOnClickListener {
            val dialogFragment = DatePickerFragment()
            dialogFragment.show(requireActivity().supportFragmentManager, "datePicker")
        }

        binding.addIncomeBtn.setOnClickListener {
            val amount = binding.addEdAmount.text.toString()
            val notes = binding.addEdNotes.text.toString()
            val category = binding.category.selectedItemPosition
            val cat = getByNumberIncome(category)
            if(!amount.isNullOrEmpty()&& !notes.isNullOrEmpty()) {
                val data = UserIncome(
                    0,
                    0,
                    amount.toLong(),
                    notes, cat, dueDateMillis)
                viewModel.saveIncome(data)
                val intent = Intent(requireActivity(), MainActivity::class.java)
                startActivity(intent)
            } else {
                val builder = AlertDialog.Builder(view.context)
                val alert = builder.create()
                builder
                    .setMessage("Input the data")
                    .setPositiveButton("Oke") { _, _ ->
                        alert.cancel()
                    }.show()
            }
        }
    }
    companion object {

    }

}