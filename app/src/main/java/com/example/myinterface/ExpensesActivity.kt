package com.example.myinterface

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.Spinner
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.MutableLiveData

class ExpensesActivity : ComponentActivity() {
    private val expensesList = mutableListOf<ExpenseItem>()
    private lateinit var expenseAdapter: ExpenseAdapter
    private val options = mutableListOf("Select an option", "Food", "Transportation", "+ Add new option")
    private lateinit var selectedOption: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_expenses)

        val addButton: Button = findViewById(R.id.addButton)
        val expenseListView: ListView = findViewById(R.id.expenseListView)

        expenseAdapter = ExpenseAdapter(this, expensesList)
        expenseListView.adapter = expenseAdapter

        val optionsSpinner: Spinner = findViewById(R.id.optionsSpinner)

        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, options)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        optionsSpinner.adapter = adapter

        optionsSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val selectedItem = options[position]
                if (selectedItem == "+ Add new option") {
                    optionsSpinner.setSelection(0) // Reset
                    showAddOptionDialog(optionsSpinner)
                } else {
                    selectedOption = selectedItem
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // Do nothing
            }
        }

        addButton.setOnClickListener {
            addExpense()
        }
    }

    private fun showAddOptionDialog(optionsSpinner: Spinner) {
        val dialogView = LayoutInflater.from(this).inflate(R.layout.expense_options, null)
        val alertDialogBuilder = AlertDialog.Builder(this, R.style.AlertDialogTheme)

        alertDialogBuilder.setView(dialogView)
        alertDialogBuilder.setTitle("Add New Option")

        val alertDialog = alertDialogBuilder.create()
        alertDialog.show()

        val btnAddOption: Button = dialogView.findViewById(R.id.btnAddOption)
        btnAddOption.setOnClickListener {
            val etNewOption: EditText = dialogView.findViewById(R.id.etNewOption)
            val newOption = etNewOption.text.toString().trim()
            if (newOption.isNotEmpty()) {
                options.add(options.size - 1, newOption)
                (optionsSpinner.adapter as ArrayAdapter<String>).notifyDataSetChanged()
                alertDialog.dismiss()
            }
        }
    }

    private fun addExpense() {
        val editText = findViewById<EditText>(R.id.editText)
        val itemName = editText.text.toString().trim()

        if (itemName.isNotEmpty()) {
            val item = ExpenseItem(itemName, selectedOption)
            expensesList.add(item)
            expenseAdapter.notifyDataSetChanged()
            editText.text.clear()
        } else {
            Toast.makeText(this, "Please enter a valid item name.", Toast.LENGTH_SHORT).show()
        }
    }
}
