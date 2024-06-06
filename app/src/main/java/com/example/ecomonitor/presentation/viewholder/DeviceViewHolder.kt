package com.example.ecomonitor.presentation.viewholder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.ecomonitor.databinding.DeviceBinding

class DeviceViewHolder(root: View) : RecyclerView.ViewHolder(root) {

    private val binding = DeviceBinding.bind(root)

    val name = binding.deviceNameTV
    val image = binding.deviceImage
    val location = binding.deviceLocationTV



}