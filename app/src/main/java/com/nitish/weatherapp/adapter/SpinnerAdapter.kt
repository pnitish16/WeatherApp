package com.nitish.weatherapp.adapter

import android.content.Context
import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.SpinnerAdapter
import android.widget.TextView
import com.nitish.weatherapp.R

class SpinnerAdapter : BaseAdapter, SpinnerAdapter {

    private var context: Context
    private var data: List<String>

    constructor(context1: Context, data: List<String>) {
        this.context = context1
        this.data = data
    }

    override fun getCount(): Int {
        return data.size
    }

    override fun getItem(position: Int): Any {
        val subjectItem = data[position]
        return subjectItem
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var convertView = convertView
        if (convertView == null) {
            val inflater = context.applicationContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            convertView = inflater.inflate(R.layout.row_spinner, parent, false)
        }

        val tv_Spinner_Item = convertView!!.findViewById(R.id.tv_Spinner_Item) as TextView
        val subjectsItem = data[position].apply { }
        tv_Spinner_Item.text = subjectsItem

        return convertView
    }

}
