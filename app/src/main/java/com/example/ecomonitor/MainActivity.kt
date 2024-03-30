package com.example.ecomonitor

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.ecomonitor.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.toRegistrationButton.setOnClickListener { register() }
    }

    private fun register() {
        val intent = Intent(this, SignUpActivity::class.java)
        startActivity(intent)
    }
}