package com.example.ecomonitor.presentation.view

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.ecomonitor.databinding.ActivityProfileBinding
import com.example.ecomonitor.domain.model.ProfileData
import com.example.ecomonitor.domain.model.TransactionStatus
import com.example.ecomonitor.domain.model.TransactionStatus.SuccessStatus
import com.example.ecomonitor.domain.model.TransactionStatus.ErrorStatus
import com.example.ecomonitor.domain.model.TransactionStatus.LoadingStatus
import com.example.ecomonitor.presentation.util.UIUtil
import com.example.ecomonitor.presentation.viewmodel.ProfileViewModel

class ProfileActivity : AppCompatActivity() {
    private val binding by lazy { ActivityProfileBinding.inflate(layoutInflater) }
    private val viewModel: ProfileViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.profileProfilePicture.setOnClickListener { changeProfilePicture() }
        binding.profileSaveBTN.setOnClickListener { changeProfileData() }
        binding.changePasswordBTN.setOnClickListener { toChangePassword() }

        viewModel.dataStatus.observe(this) { status -> updateUIForProfileData(status) }
    }

    private fun changeProfilePicture() {}
    private fun changeProfileData() {
        try { viewModel.changeProfileData(profileDataFromInput()) }
        catch (_:NumberFormatException) { UIUtil.showMessage(this, "Error: You have to type a number in CC, Phone and Age.") }
    }

    private fun profileDataFromInput(): ProfileData {
        return ProfileData(
            binding.profileCCET.text.toString().toInt(),
            binding.profileNameET.text.toString(),
            binding.profileAddressET.text.toString(),
            binding.profilePhoneET.text.toString().toInt(),
            binding.profileAgeET.text.toString().toInt(),
        )
    }

    private fun toChangePassword() {}

    private fun updateUIForProfileData(status: TransactionStatus) {
        when(status) {
            is SuccessStatus -> {
                UIUtil.showMessage(this, status.message)
                binding.profileUsername.text = binding.profileNameET.text
                binding.profileAddress.text = binding.profileAddressET.text
            }
            is ErrorStatus -> UIUtil.showMessage(this, status.message)
            is LoadingStatus -> { /* showMessage(this, status.message) */ }
        }
    }
}