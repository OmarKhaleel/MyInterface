package com.example.myinterface

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageButton
import android.widget.TextView

class ExpenseAdapter(context: Context, private val expenses: MutableList<ExpenseItem>) :
    ArrayAdapter<ExpenseItem>(context, 0, expenses) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = convertView ?: LayoutInflater.from(context)
            .inflate(R.layout.expense_item_adapter, parent, false)

        val expense = expenses[position]

        val expenseNameTextView = view.findViewById<TextView>(R.id.expenseNameTextView)
        val expenseTypeTextView = view.findViewById<TextView>(R.id.expenseTypeTextView)
        val deleteButton = view.findViewById<ImageButton>(R.id.deleteButton)
        val editTypeButton = view.findViewById<ImageButton>(R.id.editTypeButton)

        expenseNameTextView.text = expense.expenseName
        expenseTypeTextView.text = expense.expenseType

        deleteButton.setOnClickListener {
            expenses.removeAt(position)
            notifyDataSetChanged()
        }

        editTypeButton.setOnClickListener {
            showExpenseTypeDialog(position, expense.expenseType)
        }

        return view
    }

    private fun showExpenseTypeDialog(position: Int, currentType: String) {
        val expenseTypes = arrayOf("Food", "Transportation", "Other")

        val typeAdapter =
            ArrayAdapter(context, android.R.layout.simple_spinner_dropdown_item, expenseTypes)

        val dialogBuilder = AlertDialog.Builder(context)
        dialogBuilder.setTitle("Select Expense Type")
            .setAdapter(typeAdapter) { _, which ->
                val selectedType = expenseTypes[which]
                expenses[position].expenseType = selectedType
                notifyDataSetChanged()
            }
            .setNegativeButton("Cancel") { dialog, _ -> dialog.dismiss() }
            .show()
    }
}
