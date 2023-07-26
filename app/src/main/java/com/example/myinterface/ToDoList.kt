package com.example.myinterface

import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.ListView
import android.widget.Toast
import androidx.activity.ComponentActivity

class ToDoList : ComponentActivity() {

    private lateinit var itemAdapter: ItemAdapter
    private val todoItems = mutableListOf<ToDoListItem>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_to_do_list)

        itemAdapter = ItemAdapter(this, R.layout.item_adapter, todoItems)
        val listView: ListView = findViewById(R.id.listView)
        listView.adapter = itemAdapter
    }

    fun addItem(view: View) {
        val editText = findViewById<EditText>(R.id.editText)
        val itemName = editText.text.toString().trim()

        if (itemName.isNotEmpty()) {
            val item = ToDoListItem(itemName)
            todoItems.add(item)
            itemAdapter.notifyDataSetChanged()
            editText.text.clear()
        } else {
            Toast.makeText(this, "Please enter a valid item name.", Toast.LENGTH_SHORT).show()
        }
    }
}