package com.example.ecomonitor.presentation.view

import android.Manifest
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.ecomonitor.databinding.ActivityMainMenuBinding
import com.example.ecomonitor.domain.model.TransactionStatus
import com.example.ecomonitor.domain.model.TransactionStatus.SuccessStatus
import com.example.ecomonitor.domain.model.TransactionStatus.ErrorStatus
import com.example.ecomonitor.domain.model.TransactionStatus.LoadingStatus
import com.example.ecomonitor.presentation.util.UIUtil
import com.example.ecomonitor.presentation.viewmodel.MainMenuViewModel
import com.google.firebase.Firebase
import com.google.firebase.auth.auth

class MainMenuActivity : AppCompatActivity() {
    private val binding by lazy { ActivityMainMenuBinding.inflate(layoutInflater) }
    private val viewModel: MainMenuViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        requestPermissions(arrayOf(
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.POST_NOTIFICATIONS
        ), 1)

        binding.signOutButton.setOnClickListener { signOut() }
        binding.profileButton.setOnClickListener {
            val intent = Intent(this, ProfileActivity::class.java)
            startActivity(intent)
        }
        viewModel.status.observe(this) { status -> updateUI(status) }

        Firebase.auth.currentUser?.let {
            viewModel.loadUser()
            viewModel.observeMeasurements(it.uid)

            viewModel.measurementsState.observe(this) { measurements ->
                // Update Dashboard
                // Notification of the last measurement
                UIUtil.showMessage(this, "New Measurement: ${measurements.last().value}")
            }
        }


    }

    private fun signOut() {
        viewModel.signOut()
    }

    private fun updateUI(status: TransactionStatus) {
        when(status) {
            is ErrorStatus -> UIUtil.showMessage(this, status.message)
            is SuccessStatus -> { UIUtil.showMessage(this, status.message); finish() }
            is LoadingStatus -> { /* showMessage(this, status.message) */ }
        }
    }
}