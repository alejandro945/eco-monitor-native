package com.example.ecomonitor.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.ecomonitor.presentation.view.AllAlertsFragment
import com.example.ecomonitor.presentation.view.EnergyAlertsFragment
import com.example.ecomonitor.presentation.view.WaterAlertsFragment

class DeviceTabsAdapter (
    fragmentManager: FragmentManager,
    lifecycle: Lifecycle
) : FragmentStateAdapter(fragmentManager, lifecycle) {

    override fun getItemCount(): Int {
        return 3
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> AllAlertsFragment.newInstance()
            1 -> EnergyAlertsFragment.newInstance()
            2 -> WaterAlertsFragment.newInstance()
            else -> throw IllegalArgumentException("Invalid position")
        }
    }
}