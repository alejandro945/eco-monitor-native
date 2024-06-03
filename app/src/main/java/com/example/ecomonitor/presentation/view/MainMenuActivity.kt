package com.example.ecomonitor.presentation.view

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

class MainMenuActivity : AppCompatActivity() {
    private val binding by lazy { ActivityMainMenuBinding.inflate(layoutInflater) }
    private val viewModel: MainMenuViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        supportFragmentManager.beginTransaction()
            .replace(
                binding.fragmentContainer.id,
                DashboardFragment.newInstance())
            .commit()

        binding.signOutButton.setOnClickListener { signOut() }
        binding.profileButton.setOnClickListener {
            //TEST CODE.
            val intent = Intent(this, ProfileActivity::class.java)
            startActivity(intent)
            //TEST CODE.
        }

        viewModel.status.observe(this) { status -> updateUI(status) }
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