package com.example.ecomonitor.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.example.ecomonitor.domain.model.Device
import com.example.ecomonitor.presentation.viewholder.DeviceViewHolder
import com.example.ecomonitor.R

class DeviceAdapter : Adapter<DeviceViewHolder>() {

    private var devices: ArrayList<Device> = arrayListOf()

    init {
        devices.add(Device("Device 1"))
        devices.add(Device("Device 2"))
        devices.add(Device("Device 3"))
        devices.add(Device("Device 4"))
        devices.add(Device("Device 5"))
        devices.add(Device("Device 6"))
        devices.add(Device("Device 7"))
        devices.add(Device("Device 8"))
        devices.add(Device("Device 9"))
        devices.add(Device("Device 10"))
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DeviceViewHolder {
        val layoutInflater : LayoutInflater = LayoutInflater.from(parent.context)
        val view: View = layoutInflater.inflate(R.layout.device, parent, false)
        val holder = DeviceViewHolder(view)
        return holder
    }

    override fun onBindViewHolder(holder: DeviceViewHolder, position: Int) {
        holder.name.text = devices[position].name
    }

    override fun getItemCount(): Int {
        return devices.size
    }

}