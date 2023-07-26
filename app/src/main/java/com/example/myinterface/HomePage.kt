package com.example.myinterface

import android.content.Intent
import android.os.Bundle
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.GridView
import androidx.activity.ComponentActivity

class HomePage : ComponentActivity() {

    private lateinit var gridView: GridView
    private val gridOptions = arrayOf("Expenses", "ToDo List")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_page)

        gridView = findViewById(R.id.gridView)
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, gridOptions)
        gridView.adapter = adapter

        gridView.onItemClickListener = AdapterView.OnItemClickListener { _, _, position, _ ->
            val selectedOption = gridOptions[position]
            when (selectedOption) {
                "Expenses" -> startActivity(Intent(this, ExpensesActivity::class.java))
                "ToDo List" -> startActivity(Intent(this, ToDoList::class.java))
                else -> {}
            }
        }
    }
}