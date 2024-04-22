package com.example.ecomonitor.presentation.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import com.example.ecomonitor.databinding.ActivitySignInBinding
import com.example.ecomonitor.domain.model.AuthenticationStatus
import com.example.ecomonitor.presentation.util.GoogleSignInUtil
import com.example.ecomonitor.presentation.util.UIUtil.Companion.showMessage
import com.example.ecomonitor.presentation.viewmodel.SignInViewModel

class SignInActivity : AppCompatActivity() {
    private val binding by lazy { ActivitySignInBinding.inflate(layoutInflater) }
    private val viewModel: SignInViewModel by viewModels()

    private lateinit var googleSignInLauncher: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        viewModel.logEvent(this, "first_app_launch")

        binding.signInButton.setOnClickListener { signIn() }
        binding.googleSignInButton.setOnClickListener { signInWithGoogle() }
        binding.toRegistrationButton.setOnClickListener { toSignUpScreen() }

        viewModel.status.observe(this) { status -> updateUI(status) }

        googleSignInLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult(), ::afterGoogleSignIn)
    }

    private fun signIn() {
        val email = binding.signInEmailField.text.toString()
        val password = binding.signInPasswordField.text.toString()
        viewModel.signIn(email, password)
    }

    private fun signInWithGoogle() {
        val intent = GoogleSignInUtil.createGoogleSignInIntent(this)
        googleSignInLauncher.launch(intent)
    }

    private fun afterGoogleSignIn(result: ActivityResult) {
        result.data?.let {
            val token = GoogleSignInUtil.retrieveTokenFromIntent(it)
            viewModel.signIn(token)
        }
    }

    private fun toSignUpScreen() {
        val intent = Intent(this, SignUpActivity::class.java)
        startActivity(intent)
    }

    private fun updateUI(status: AuthenticationStatus) {
        when(status) {
            is AuthenticationStatus.SuccessStatus -> toMainMenu()
            is AuthenticationStatus.ErrorStatus -> showMessage(this, status.message)
            is AuthenticationStatus.LoadingStatus -> { /* showMessage(this, status.message) */ }
        }
    }

    private fun toMainMenu() {
        val intent = Intent(this, MainMenuActivity::class.java)
        startActivity(intent)
    }
}