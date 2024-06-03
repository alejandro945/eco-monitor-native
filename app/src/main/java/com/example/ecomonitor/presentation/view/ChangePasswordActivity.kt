package com.example.ecomonitor.presentation.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.ecomonitor.databinding.ActivityChangePasswordBinding

class ChangePasswordActivity : AppCompatActivity() {
    private val binding by lazy { ActivityChangePasswordBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }
}