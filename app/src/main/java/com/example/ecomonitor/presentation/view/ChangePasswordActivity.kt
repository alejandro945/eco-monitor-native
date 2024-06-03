package com.example.ecomonitor.presentation.view

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.ecomonitor.databinding.ActivityChangePasswordBinding
import com.example.ecomonitor.domain.model.TransactionStatus
import com.example.ecomonitor.domain.model.TransactionStatus.SuccessStatus
import com.example.ecomonitor.domain.model.TransactionStatus.ErrorStatus
import com.example.ecomonitor.domain.model.TransactionStatus.LoadingStatus
import com.example.ecomonitor.domain.model.TransactionStatus.Companion.MISMATCH_MESSAGE
import com.example.ecomonitor.presentation.util.UIUtil
import com.example.ecomonitor.presentation.viewmodel.ChangePasswordViewModel

class ChangePasswordActivity() : AppCompatActivity() {
    private val binding by lazy { ActivityChangePasswordBinding.inflate(layoutInflater) }
    private val viewModel: ChangePasswordViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        viewModel.status.observe(this) { status -> updateUI(status) }

        binding.changePassSaveBTN.setOnClickListener { changePassword() }
        binding.PASSchangePasswordBackBTN.setOnClickListener { finish() }
    }

    private fun changePassword() {
        val password = binding.changePassPassET.text.toString()
        val confirmPassword = binding.changePassConfirmET.text.toString()
        if (password == confirmPassword) { viewModel.updatePassword(password) }
        else {UIUtil.showMessage(this, MISMATCH_MESSAGE)}
    }

    private fun updateUI(status: TransactionStatus) {
        when(status) {
            is SuccessStatus -> UIUtil.showMessage(this, status.message)
            is ErrorStatus -> UIUtil.showMessage(this, status.message)
            is LoadingStatus -> { /* showMessage(this, status.message) */ }
        }
    }
}