package com.example.ecomonitor.presentation.view

import android.content.Intent
import android.media.Image
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.example.ecomonitor.databinding.ActivityAlertsBinding
import com.google.android.material.tabs.TabItem
import com.google.android.material.tabs.TabLayout
import com.example.ecomonitor.R
import com.example.ecomonitor.databinding.ActivityDevicesBinding
import com.example.ecomonitor.presentation.util.AlertsAdapter
import com.example.ecomonitor.presentation.util.DevicesAdapter
import com.example.ecomonitor.presentation.viewmodel.AlertViewModel
import com.example.ecomonitor.presentation.viewmodel.DeviceViewModel


class AlertsActivity : AppCompatActivity() {

    private val binding by lazy { ActivityAlertsBinding.inflate(layoutInflater) }
    private val adapter = AlertsAdapter(arrayListOf())
    private val viewModel: AlertViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_alerts)

        val recycler = findViewById<RecyclerView>(R.id.AlertsRecyclerView)
        recycler.setHasFixedSize(true)
        recycler.layoutManager = LinearLayoutManager(this)

        recycler.adapter = adapter

        val returnButton = findViewById<ImageView>(R.id.returnButton)
        returnButton.setOnClickListener {
            val intent = Intent(this, MainMenuActivity::class.java)
            startActivity(intent)
        }

        viewModel.alerts.observe(this) { alerts ->
            Log.d("AlertsActivity", "Alerts list updated: ${alerts.size}")  // Añade un log aquí para verificar
            adapter.updateData(alerts)
        }

        viewModel.getAlerts()


    }


}