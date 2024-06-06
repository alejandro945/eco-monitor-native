package com.example.ecomonitor.presentation.view

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.ecomonitor.R
import com.example.ecomonitor.databinding.ActivityDevicesBinding
import com.example.ecomonitor.databinding.ActivityNewDeviceBinding
import com.example.ecomonitor.domain.model.Device
import com.example.ecomonitor.presentation.viewmodel.DeviceViewModel

class NewDeviceActivity: AppCompatActivity()  {

    private val viewModel: DeviceViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_device)

        val back = findViewById<Button>(R.id.back)
        val saveDevice = findViewById<Button>(R.id.saveDevice)
        val deviceNameET = findViewById<EditText>(R.id.deviceNameET)
        val locationET = findViewById<EditText>(R.id.locationET)
        back.setOnClickListener { finish() }
        saveDevice.setOnClickListener {
            viewModel.addDevice(
                Device(
                    deviceNameET.text.toString(),
                    locationET.text.toString(),
                )
            )
            finish()
        }
    }

}
