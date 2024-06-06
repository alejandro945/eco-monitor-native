package com.example.ecomonitor.presentation.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.example.ecomonitor.R
import com.example.ecomonitor.adapter.DeviceAdapter
import com.example.ecomonitor.adapter.DeviceTabsAdapter
import com.example.ecomonitor.databinding.ActivityDevicesBinding
import com.example.ecomonitor.databinding.DeviceListBinding
import com.google.android.material.tabs.TabLayout

class DevicesActivity : AppCompatActivity() {

    private lateinit var binding: DeviceListBinding

    private lateinit var tabLayout: TabLayout
    private lateinit var viewPager: ViewPager2
    private lateinit var deviceTabsAdapter: DeviceTabsAdapter
    private lateinit var deviceAdapter: DeviceAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DeviceListBinding.inflate(layoutInflater)

        setContentView(binding.root)

        tabLayout = findViewById(R.id.tabLayout)
        viewPager = findViewById(R.id.viewPager)

        deviceTabsAdapter = DeviceTabsAdapter(supportFragmentManager, lifecycle)

        viewPager.adapter = deviceTabsAdapter

        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                tab?.let {
                    viewPager.currentItem = it.position
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                // Do nothing
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
                // Do nothing
            }
        })

        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                tabLayout.getTabAt(position)?.select()
            }
        })

        deviceAdapter = DeviceAdapter()
        binding.deviceList.adapter = deviceAdapter
        binding.deviceList.layoutManager = LinearLayoutManager(this)
        binding.deviceList.setHasFixedSize(true)


    }


}