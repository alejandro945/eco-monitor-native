package com.example.ecomonitor.presentation.view

import android.Manifest
import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.ecomonitor.databinding.ActivityMainMenuBinding
import com.example.ecomonitor.domain.model.TransactionStatus
import com.example.ecomonitor.domain.model.TransactionStatus.SuccessStatus
import com.example.ecomonitor.domain.model.TransactionStatus.ErrorStatus
import com.example.ecomonitor.domain.model.TransactionStatus.LoadingStatus
import com.example.ecomonitor.presentation.util.UIUtil
import com.example.ecomonitor.presentation.viewmodel.MainMenuViewModel

class MainMenuActivity : AppCompatActivity() {
    private val binding by lazy { ActivityMainMenuBinding.inflate(layoutInflater) }
    private val viewModel: MainMenuViewModel by viewModels()

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        requestPermissions(arrayOf(
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.POST_NOTIFICATIONS
        ), 1)

        supportFragmentManager.beginTransaction()
            .replace(
                binding.fragmentContainer.id,
                DashboardFragment.newInstance())
            .commit()

        binding.signOutButton.setOnClickListener { signOut() }
        binding.profileButton.setOnClickListener {
            val intent = Intent(this, ProfileActivity::class.java)
            startActivity(intent)
        }
        binding.goToDevices.setOnClickListener {
            val intent = Intent(this, DevicesActivity::class.java)
            startActivity(intent)
        }

        viewModel.status.observe(this) { status -> updateUI(status) }

        viewModel.measurementsState.observe(this) { measurements ->
            if (measurements.isNotEmpty()) {
                UIUtil.showMessage(this, "New measurement: ${ measurements.last().value }")
            }
        }

        viewModel.loadUser()
        viewModel.observeMeasurements()
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