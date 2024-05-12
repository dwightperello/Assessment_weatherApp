package com.example.weatherapp_assessment.presenter.weather.dashboard

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapp_assessment.R
import com.example.weatherapp_assessment.data.local.model.weather

class weatherAdapter (private val fragment: Fragment): RecyclerView.Adapter<weatherAdapter.ViewHolder> (){
    private var items:List<weather> = listOf()
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        // Holds the TextView that will add each item to
        val tempTextView: TextView = view.findViewById(R.id.txttemps)
        val temps: TextView = view.findViewById(R.id.txtdatetemps)
        val sunrise: TextView = view.findViewById(R.id.txtsunrises)
        val sunset: TextView = view.findViewById(R.id.txtsunsets)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.adapter_weather, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val weatherItem= items[position]
        holder.tempTextView.text= weatherItem.temp
        holder.temps.text= weatherItem.description
        holder.sunrise.text= weatherItem.sunrise
        holder.sunset.text= weatherItem.sunset
    }

    fun confirmOrder(list: List<weather>) {
        items = list
        notifyDataSetChanged()
    }
}