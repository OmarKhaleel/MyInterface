package com.example.myinterface

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.CheckBox
import android.widget.TextView

class ItemAdapter(context: Context, private val resourceId: Int, private val items: MutableList<Item>) :
    ArrayAdapter<Item>(context, resourceId, items) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view: View = convertView ?: LayoutInflater.from(context).inflate(resourceId, null)

        val itemTextView: TextView = view.findViewById(R.id.ItemTV)
        val checkBox: CheckBox = view.findViewById(R.id.CheckBox)
        val deleteBTN: Button = view.findViewById(R.id.DeleteBTN)

        val item = items[position]
        itemTextView.text = item.name
        checkBox.isChecked = item.isChecked

        checkBox.setOnClickListener {
            item.isChecked = checkBox.isChecked
        }

        deleteBTN.setOnClickListener {
            removeItem(position)
        }

        return view
    }

    private fun removeItem(position: Int) {
        items.removeAt(position)
        notifyDataSetChanged()
    }
}
