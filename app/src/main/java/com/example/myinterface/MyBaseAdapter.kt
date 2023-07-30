package com.example.myinterface

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView

class MyBaseAdapter(val context: Context, val data: ArrayList<GridViewItem>?) : BaseAdapter() {
    override fun getCount(): Int {
        return data?.size ?: 0
    }

    override fun getItem(position: Int): Any? {
        if (data != null)
            return data[position]
        else
            return null
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, viewGroup: ViewGroup?): View? {
        var convertView = convertView
        val holder: MyBaseAdapter.MyViewHolder
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.grid_item_layout, null, false)
            holder = MyBaseAdapter.MyViewHolder()
            holder.flagIV = convertView!!.findViewById(R.id.imageView)
            holder.nameTV = convertView.findViewById(R.id.itemTextView)
            convertView.setTag(holder)
        } else {
            holder = convertView.tag as MyBaseAdapter.MyViewHolder
        }
        holder.flagIV!!.setImageResource(data!![position].flag)
        holder.nameTV!!.text = data[position].name
        return convertView
    }

    private class MyViewHolder {
        var flagIV: ImageView? = null
        var nameTV: TextView? = null
    }
}