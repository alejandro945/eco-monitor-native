package com.example.ecomonitor.presentation.view

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.ecomonitor.R
import com.example.ecomonitor.databinding.ActivityMainMenuBinding
import com.example.ecomonitor.domain.model.AuthenticationStatus
import com.example.ecomonitor.presentation.util.UIUtil
import com.example.ecomonitor.presentation.viewmodel.MainMenuViewModel

class MainMenuActivity : AppCompatActivity() {
    private val binding by lazy { ActivityMainMenuBinding.inflate(layoutInflater) }
    private val viewModel: MainMenuViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.signOutButton.setOnClickListener { signOut() }

        viewModel.status.observe(this) { status -> updateUI(status) }
    }

    private fun signOut() {
        viewModel.signOut()
    }

    private fun updateUI(status: AuthenticationStatus) {
        when(status) {
            is AuthenticationStatus.SuccessStatus -> {
                UIUtil.showMessage(this, status.message); finish() }
            is AuthenticationStatus.ErrorStatus -> UIUtil.showMessage(this, status.message)
            is AuthenticationStatus.LoadingStatus -> { /* showMessage(this, status.message) */ }
        }
    }
}