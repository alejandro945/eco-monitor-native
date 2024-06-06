package com.example.ecomonitor.presentation.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.example.ecomonitor.databinding.ActivityAlertsBinding
import com.google.android.material.tabs.TabItem
import com.google.android.material.tabs.TabLayout
import com.example.ecomonitor.R


class AlertsActivity : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = ActivityAlertsBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    companion object {
        fun newInstance(): AlertsActivity {
            return AlertsActivity()
        }
    }

}