package com.example.ecomonitor.presentation.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.ecomonitor.R
import com.example.ecomonitor.databinding.ActivityDevicesBinding

class DevicesActivity : AppCompatActivity() {

    private val binding by lazy { ActivityDevicesBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }
}