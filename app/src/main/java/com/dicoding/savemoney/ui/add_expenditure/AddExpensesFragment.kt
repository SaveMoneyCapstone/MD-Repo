package com.dicoding.savemoney.ui.add_expenditure

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.dicoding.savemoney.data.UserData

import com.dicoding.savemoney.databinding.FragmentAddExpensesBinding
import com.dicoding.savemoney.ui.ViewModelFactory
import com.dicoding.savemoney.ui.add.AddExpenseViewModel
import com.dicoding.savemoney.utils.DatePickerFragment
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
class AddExpensesFragment : Fragment(), DatePickerFragment.DialogDateListener {
    private var dueDateMillis: Long = System.currentTimeMillis()
    private val binding by lazy { FragmentAddExpensesBinding.inflate(layoutInflater)}
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

        binding.addExpensesBtn.setOnClickListener {
            val amount = binding.addEdAmount.text.toString().toLong()
            val notes = binding.addEdNotes.text.toString()
            val category = binding.category.selectedItemPosition
            val data = UserData(0, amount, notes, category, dueDateMillis)
            viewModel.saveData(data)

        }
    }
    companion object {
    }

    override fun onDialogDateSet(tag: String?, year: Int, month: Int, dayOfMonth: Int) {
        val calendar = Calendar.getInstance()
        calendar.set(year, month, dayOfMonth)
        val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        with(binding) {
            addTvDueDate.text = dateFormat.format(calendar.time)
            dueDateMillis = calendar.timeInMillis
        }
    }

}