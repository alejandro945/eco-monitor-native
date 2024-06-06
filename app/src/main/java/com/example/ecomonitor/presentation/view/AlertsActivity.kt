package com.example.ecomonitor.presentation.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.example.ecomonitor.databinding.ActivityAlertsBinding
import com.google.android.material.tabs.TabItem
import com.google.android.material.tabs.TabLayout


class AlertsActivity : AppCompatActivity() {

    private val binding by lazy { ActivityAlertsBinding.inflate(layoutInflater) }

    private lateinit var tabLayout: TabLayout
    private lateinit var viewPager: ViewPager2
    private lateinit var alertsFragmentAdapter: AlertsFragmentAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        tabLayout = binding.tabLayout
        viewPager = binding.viewPager

        alertsFragmentAdapter = AlertsFragmentAdapter(supportFragmentManager, lifecycle)

        viewPager.adapter = alertsFragmentAdapter

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

    }

}