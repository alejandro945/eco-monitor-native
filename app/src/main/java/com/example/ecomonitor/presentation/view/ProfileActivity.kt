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

        viewModel.profileData.observe(this) { profileData -> initializeProfileData(profileData) }
        viewModel.dataStatus.observe(this) { status -> updateProfileUI(status) }
        viewModel.pictureStatus.observe(this) {}

        viewModel.retrieveProfileData()

        binding.profileProfilePicture.setOnClickListener { changeProfilePicture() }
        binding.profileSaveBTN.setOnClickListener { changeProfileData() }
        binding.changePasswordBTN.setOnClickListener { toChangePassword() }
        binding.profileBackBTN.setOnClickListener { finish() }
    }

    private fun initializeProfileData(profileData: ProfileData?) {
        if (profileData != null) {
            binding.profileUsername.text = profileData.name
            binding.profileAddress.text = profileData.address
            binding.profileNameET.text.append(profileData.name)
            binding.profileAddressET.text.append(profileData.address)
            binding.profileEmailET.text.append(profileData.email)

            binding.profileCCET.text.append(
                if (profileData.documentId == (-1).toLong()) "" else profileData.documentId.toString()
            )
            binding.profilePhoneET.text.append(
                if (profileData.phone == (-1).toLong()) "" else profileData.phone.toString()
            )
            binding.profileAgeET.text.append(
                if (profileData.age == (-1).toLong()) "" else profileData.age.toString()
            )
        } else { UIUtil.showMessage(this, "Error: You're not signed in the application.") }
    }

    private fun changeProfilePicture() {}
    private fun changeProfileData() {
        try { viewModel.changeProfileData(getInputProfileData()) }
        catch (_:NumberFormatException) {
            UIUtil.showMessage(this, "ERROR: You have to introduce a numeric value in CC, Phone and Age.")
        }
    }

    private fun getInputProfileData(): ProfileData {
        return ProfileData(
            binding.profileCCET.text.toString().toLong(),
            binding.profileNameET.text.toString(),
            binding.profileAddressET.text.toString(),
            binding.profileEmailET.text.toString(),
            binding.profilePhoneET.text.toString().toLong(),
            binding.profileAgeET.text.toString().toLong(),
        )
    }

    private fun toChangePassword() {}

    private fun updateProfileUI(status: TransactionStatus) {
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