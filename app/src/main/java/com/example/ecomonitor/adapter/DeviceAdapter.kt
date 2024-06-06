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
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DeviceViewHolder {
        val layoutInflater : LayoutInflater = LayoutInflater.from(parent.context)
        val view: View = layoutInflater.inflate(R.layout.device, parent, false)
        val holder = DeviceViewHolder(view)
        return holder
    }

    override fun onBindViewHolder(holder: DeviceViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        return devices.size
    }

}