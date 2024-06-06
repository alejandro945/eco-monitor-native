package com.example.ecomonitor.presentation.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.example.ecomonitor.databinding.ActivityAlertsBinding
import com.google.android.material.tabs.TabItem
import com.google.android.material.tabs.TabLayout
import com.example.ecomonitor.R


class AlertsActivity : AppCompatActivity() {

    private val binding by lazy { ActivityAlertsBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

    }

    fun showFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().replace(R.id.fragmentContainer, fragment).commit()
    }

}