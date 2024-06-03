package com.example.ecomonitor.presentation.view

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.ecomonitor.databinding.ActivityProfileBinding
import com.example.ecomonitor.domain.model.ProfileData
import com.example.ecomonitor.domain.model.AuthenticationStatus
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

        viewModel.dataStatus.observe(this) {status -> updateUIForProfileData(status)}
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

    private fun updateUIForProfileData(status: AuthenticationStatus) {
        when(status) {
            is AuthenticationStatus.SuccessStatus -> UIUtil.showMessage(this, status.message)
            is AuthenticationStatus.ErrorStatus -> UIUtil.showMessage(this, status.message)
            is AuthenticationStatus.LoadingStatus -> { /* showMessage(this, status.message) */ }
        }
    }
}