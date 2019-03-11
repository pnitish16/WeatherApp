package com.nitish.weatherapp.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.nitish.weatherapp.R
import com.nitish.weatherapp.database.RainfallResponse
import com.nitish.weatherapp.database.TempResponse
import com.nitish.weatherapp.database.TminResponse
import java.util.Locale

/**
 * [RecyclerView.Adapter] that can display a [TempResponse] and makes a call to the
 * TODO: Replace the implementation with code for your data type.
 */
class TempRecyclerViewAdapter(
    private val mValues: List<TempResponse>,
    private val mValues1: List<TminResponse>,
    private val mValues2: List<RainfallResponse>
) : RecyclerView.Adapter<TempRecyclerViewAdapter.ViewHolder>() {

    interface OnClickListener {
        fun onClick(item: TempResponse)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.temp_list_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.mItem = mValues[position]
        holder.mItem1 = mValues1[position]
        holder.mItem2 = mValues2[position]
        holder.tvDate.text = String.format(Locale.US, "%s-%d", getMonth(mValues[position].month), mValues[position].year)
        holder.tvMinTemp.text = String.format("Min. Temp : %s °C", holder.mItem!!.value)
        holder.tvMaxTemp.text = String.format("Max. Temp : %s °C", holder.mItem1!!.value)
        holder.tvRainfall.text = String.format("Rainfall : %s mm", holder.mItem2!!.value)
    }

    override fun getItemCount(): Int {
        return mValues.size
    }

    inner class ViewHolder internal constructor(internal val mView: View) : RecyclerView.ViewHolder(mView) {
        internal val tvDate: TextView = mView.findViewById(R.id.tvDate)
        internal val tvMinTemp: TextView = mView.findViewById(R.id.tvMinTemp)
        internal val tvMaxTemp: TextView = mView.findViewById(R.id.tvMaxTemp)
        internal val tvRainfall: TextView = mView.findViewById(R.id.tvRainfall)
        internal var mItem: TempResponse? = null
        internal var mItem1: TminResponse? = null
        internal var mItem2: RainfallResponse? = null
    }

    private val monthList = listOf("Jan","Feb","Mar", "Apr", "May" ,"Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec")

    private fun getMonth(month : Int): String{
        return monthList[month-1]
    }

}
