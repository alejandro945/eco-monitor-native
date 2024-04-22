package com.example.ecomonitor.presentation.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.example.ecomonitor.databinding.ActivitySignUpBinding
import com.example.ecomonitor.domain.enum.Role
import com.example.ecomonitor.domain.model.AuthenticationStatus
import com.example.ecomonitor.domain.model.User
import com.example.ecomonitor.presentation.util.UIUtil.Companion.showMessage
import com.example.ecomonitor.presentation.viewmodel.SignUpViewModel

class SignUpActivity: AppCompatActivity() {
    private val binding by lazy { ActivitySignUpBinding.inflate(layoutInflater) }
    private val viewModel: SignUpViewModel by viewModels()
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.createAccountButton.setOnClickListener { signUpWithEmailAndPassword() }
        binding.textView10.setOnClickListener { finish() }

        viewModel.status.observe(this) { status -> updateUI(status) }
    }

    private fun signUpWithEmailAndPassword() {
        val name = binding.nameField.text.toString()
        val email = binding.emailField.text.toString()
        val password = binding.passwordField.text.toString()
        val repeatPassword = binding.confirmPasswordField.text.toString()
        val role = binding.roleField.text.toString()
        val user = User(name, email, password, repeatPassword, Role.valueOf(role))
        viewModel.signUp(user)
    }

    private fun updateUI(status: AuthenticationStatus) {
        when(status) {
            is AuthenticationStatus.SuccessStatus -> { showMessage(this, status.message); finish() }
            is AuthenticationStatus.ErrorStatus -> showMessage(this, status.message)
            is AuthenticationStatus.LoadingStatus -> { /* showMessage(this, status.message) */ }
        }
    }
}