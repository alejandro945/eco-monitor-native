package com.example.ecomonitor

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts.StartActivityForResult
import androidx.activity.viewModels
import com.example.ecomonitor.databinding.ActivitySignUpBinding
import com.example.ecomonitor.model.AuthenticationStatus
import com.example.ecomonitor.util.GoogleSignInUtil
import com.example.ecomonitor.viewmodel.SignUpViewModel

class SignUpActivity: AppCompatActivity() {
    private val binding by lazy { ActivitySignUpBinding.inflate(layoutInflater) }
    private val viewModel: SignUpViewModel by viewModels()

    private lateinit var googleSignInLauncher: ActivityResultLauncher<Intent>
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.createAccountButton.setOnClickListener { signUpWithEmailAndPassword() }
        binding.googleSignUpButton.setOnClickListener { signUpWithGoogle() }
        binding.textView10.setOnClickListener { finish() }

        viewModel.status.observe(this) { status -> updateUI(status) }

        googleSignInLauncher = registerForActivityResult(StartActivityForResult(), ::afterGoogleSignUp)
    }

    private fun signUpWithEmailAndPassword() {
        val email = binding.emailField.text.toString()
        val password = binding.passwordField.text.toString()
        val repeatPassword = binding.confirmPasswordField.text.toString()
        viewModel.signUp(email, password, repeatPassword)
    }

    private fun signUpWithGoogle() {
        val intent = GoogleSignInUtil.createGoogleSignInIntent(this)
        googleSignInLauncher.launch(intent)
    }

    private fun afterGoogleSignUp(result: ActivityResult) {
        result.data?.let {
            val token = GoogleSignInUtil.retrieveTokenFromIntent(it)
            viewModel.signIn(token)
        }
    }

    private fun updateUI(status: AuthenticationStatus) {
        when(status) {
            is AuthenticationStatus.SuccessStatus -> { showMessage(status.message); finish() }
            is AuthenticationStatus.ErrorStatus -> showMessage(status.message)
            is AuthenticationStatus.LoadingStatus -> showMessage(status.message)
        }
    }

    private fun showMessage(text: String) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
    }
}