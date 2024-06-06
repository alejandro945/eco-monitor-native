package com.example.ecomonitor.presentation.view

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.ecomonitor.databinding.ActivityDevicesBinding
import com.example.ecomonitor.databinding.ActivityNewDeviceBinding
import com.example.ecomonitor.domain.model.Device
import com.example.ecomonitor.presentation.viewmodel.DeviceViewModel

class NewDeviceActivity: AppCompatActivity()  {
    private val binding by lazy { ActivityNewDeviceBinding.inflate(layoutInflater) }

    private val viewModel: DeviceViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.back.setOnClickListener { finish() }
        binding.saveDevice.setOnClickListener {
            viewModel.addDevice(
                Device(
                    binding.deviceNameET.text.toString(),
                    binding.locationET.text.toString(),
                )
            )
            finish()
        }
    }

}
