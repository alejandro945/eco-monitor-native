package com.example.ecomonitor.presentation.util

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.ecomonitor.R
import com.example.ecomonitor.databinding.AlertBinding
import com.example.ecomonitor.databinding.DeviceBinding
import com.example.ecomonitor.domain.model.Alert
import com.example.ecomonitor.domain.model.Device
import com.example.ecomonitor.domain.model.Profile

class AlertsAdapter(var data: ArrayList<Alert>) : RecyclerView.Adapter<AlertVH>() {

    //Listeners
    var chatButtonAction: ((Profile) -> Unit)? = null

    /*init {
        data.add(Device("Device 1"))
        data.add(Device("Device 2"))
        data.add(Device("Device 3"))
        data.add(Device("Device 4"))
        data.add(Device("Device 5"))
        data.add(Device("Device 6"))
        data.add(Device("Device 7"))
        data.add(Device("Device 8"))
        data.add(Device("Device 9"))
        data.add(Device("Device 10"))
    }*/

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlertVH {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.alert, parent, false)
        return AlertVH(view)
    }

    override fun getItemCount(): Int = data.size

    fun updateData(newData: List<Alert>) {
        data.clear()
        data.addAll(newData)
        notifyDataSetChanged()  // Asegúrate de llamar a notifyDataSetChanged
    }

    override fun onBindViewHolder(holder: AlertVH, position: Int) {
        Log.d("AlertAdapter", "Binding alert at position $position: ${data[position]}")
        holder.name.text = data[position].name
        holder.date.text = data[position].date.toString()
    }
}

class AlertVH(root: View) : RecyclerView.ViewHolder(root) {
    private val binding = AlertBinding.bind(root)
    val name = binding.alertNameTV
    val image = binding.alertImage
    val date = binding.alertDateTV
}