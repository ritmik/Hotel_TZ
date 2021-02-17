package com.example.hoteltz.ui.main

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.hoteltz.R
import com.example.hoteltz.data.HotelData
import com.example.hoteltz.data.convert

/**
 * @author Mustafin Rufat (@mustafin)
 */
class AdapterRV : RecyclerView.Adapter<AdapterRV.ViewHolder>() {
    init {
        Log.d("DataRepository", "Created AdapterRV")
    }

    var items = arrayListOf<HotelData>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    var itemClickListener: ((hotelData: HotelData) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_recyclerview_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(position)
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private var idView: TextView = view.findViewById(R.id.recycleview_item_id)
        private var nameView: TextView = view.findViewById(R.id.recycleview_item_name)
        private var addressView: TextView = view.findViewById(R.id.recycleview_item_address)
        private var starsView: TextView = view.findViewById(R.id.recycleview_item_stars)
        private var distanceView: TextView = view.findViewById(R.id.recycleview_item_distance)
        private var suitesAvailabilityView: TextView =
            view.findViewById(R.id.recycleview_item_suites_availability)
        private var rvItem: View = view.findViewById(R.id.RV_item)

        fun bind(position: Int) {
            val v = convert(items[position])
            idView.text = v.id
            nameView.text = v.name
            addressView.text = v.address
            starsView.text = v.stars
            distanceView.text = v.distance
            suitesAvailabilityView.text = v.suitesAvailability

            rvItem.setOnClickListener { _ ->
                itemClickListener?.invoke(items[position])
            }
        }
    }
}