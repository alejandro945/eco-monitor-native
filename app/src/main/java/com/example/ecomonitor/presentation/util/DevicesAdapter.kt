package com.example.ecomonitor.presentation.util

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.ecomonitor.R
import com.example.ecomonitor.databinding.DeviceBinding
import com.example.ecomonitor.databinding.UserListItemBinding
import com.example.ecomonitor.domain.model.Device
import com.example.ecomonitor.domain.model.Profile

class DevicesAdapter(var data: ArrayList<Device>) : RecyclerView.Adapter<DeviceVH>() {

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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DeviceVH {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.device, parent, false)
        return DeviceVH(view)
    }

    override fun getItemCount(): Int = data.size

    fun updateData(newData: List<Device>) {
        data.clear()
        data.addAll(newData)
        notifyDataSetChanged()  // Asegúrate de llamar a notifyDataSetChanged
    }

    override fun onBindViewHolder(holder: DeviceVH, position: Int) {
        Log.d("DeviceAdapter", "Binding device at position $position: ${data[position]}")
        holder.name.text = data[position].name
        holder.location.text = data[position].location
    }

}

class DeviceVH(root: View) : RecyclerView.ViewHolder(root) {
    private val binding = DeviceBinding.bind(root)
    val name = binding.deviceNameTV
    val image = binding.deviceImage
    val location = binding.deviceLocationTV
}







