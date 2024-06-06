package com.example.ecomonitor.presentation.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import androidx.activity.viewModels
import androidx.lifecycle.ReportFragment.Companion.reportFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.example.ecomonitor.R
import com.example.ecomonitor.adapter.DeviceAdapter
import com.example.ecomonitor.adapter.DeviceTabsAdapter
import com.example.ecomonitor.databinding.ActivityDevicesBinding
import com.example.ecomonitor.databinding.ActivitySystemUsersBinding
import com.example.ecomonitor.presentation.util.DevicesAdapter
import com.example.ecomonitor.presentation.viewmodel.DeviceViewModel
import com.example.ecomonitor.presentation.viewmodel.SystemUsersViewModel
import com.google.android.material.tabs.TabLayout

class DevicesActivity : AppCompatActivity() {

    private val binding by lazy { ActivityDevicesBinding.inflate(layoutInflater) }
    private val adapter = DevicesAdapter(arrayListOf())
    private val viewModel: DeviceViewModel by viewModels()

    //private lateinit var deviceAdapter: DeviceAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_devices)

        val recycler = findViewById<RecyclerView>(R.id.devicesRecyclerView)
        recycler.setHasFixedSize(true)
        recycler.layoutManager = LinearLayoutManager(this)

        recycler.adapter = adapter

        viewModel.devices.observe(this) { devices ->
            Log.d("SystemUsersActivity", "Users list updated: ${devices.size}")  // Añade un log aquí para verificar
            adapter.updateData(devices)
        }

        viewModel.getDevices()

        val deviceBackBtn = findViewById<ImageView>(R.id.deviceBackBTN)
        deviceBackBtn.setOnClickListener { finish() }

        val addDeviceBtn = findViewById<Button>(R.id.addDevice)

        addDeviceBtn.setOnClickListener{
            val intent = Intent(this, NewDeviceActivity::class.java)
            startActivity(intent)
        }

    }




}