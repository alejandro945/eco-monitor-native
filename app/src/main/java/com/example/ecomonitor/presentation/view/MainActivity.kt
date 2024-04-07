package com.example.ecomonitor.presentation.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.ecomonitor.R
import com.example.ecomonitor.data.observability.FirebaseAnalytics
import com.example.ecomonitor.databinding.ActivityMainBinding
import com.example.ecomonitor.domain.interfaces.IObservability


class MainActivity : AppCompatActivity() {

    private lateinit var observability: IObservability

    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        //TODO: Move this to view-model package when we have already implemented
        observability = FirebaseAnalytics(this)
        observability.log(com.google.firebase.analytics.FirebaseAnalytics.Event.SELECT_CONTENT)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setContentView(binding.root)
    }

}