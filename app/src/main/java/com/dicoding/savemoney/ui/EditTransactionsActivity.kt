package com.dicoding.savemoney.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import com.dicoding.savemoney.R
import com.dicoding.savemoney.data.model.TransactionModel
import com.dicoding.savemoney.databinding.ActivityDetailTransactionsBinding
import com.dicoding.savemoney.databinding.ActivityEditTransactionsBinding

class EditTransactionsActivity : AppCompatActivity() {
    private val binding by lazy { ActivityEditTransactionsBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val edit = intent.getParcelableExtra<TransactionModel>(ID)
        if(edit != null) {
            with(binding) {
                addEdAmount.text = edit.amount.toEditable()
                addEdDescription.text = edit.note.toEditable()
              //  spCategory.adapter = edit.category.

            }
        }


    }

    fun Double?.toEditable() : Editable = Editable.Factory.getInstance().newEditable(this.toString())
    fun String?.toEditable() : Editable = Editable.Factory.getInstance().newEditable(this)

    companion object {
        val ID = "id"
    }
}