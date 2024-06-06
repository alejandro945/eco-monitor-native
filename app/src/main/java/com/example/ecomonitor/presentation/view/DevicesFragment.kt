package com.example.ecomonitor.presentation.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ecomonitor.adapter.DeviceAdapter
import com.example.ecomonitor.databinding.DeviceListBinding
import com.example.ecomonitor.databinding.WaterAlertsFragmentBinding

class DevicesFragment: Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = DeviceListBinding.inflate(inflater, container, false)
        val deviceAdapter = DeviceAdapter()

        binding.deviceList.setHasFixedSize(true)
        binding.deviceList.layoutManager = LinearLayoutManager(super.getContext())

        binding.deviceList.adapter = deviceAdapter

        binding.addDeviceBtn.setOnClickListener {
            //
        }

        return binding.root
    }

    companion object {
        fun newInstance(): DevicesFragment {
            return DevicesFragment()
        }
    }
}