package com.example.ecomonitor

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import com.example.ecomonitor.databinding.ActivityMainBinding
import com.example.ecomonitor.domain.model.AppAuthState
import com.example.ecomonitor.viewmodel.LoginViewModel

class MainActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    val viewModel : LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        // binding.loginButton listener
        binding.loginBtn.setOnClickListener {
            viewModel.login(
                binding.userText.text.toString(),
                binding.passwordText.text.toString()
            )
        }

        // binding.signUpButton listener

        // binding.googleLogIn listener

        // binding.appleLogIn listener

        // viewModel.observe the authStatus
        viewModel.authStatus.observe(this) {
            when (it) {
                is AppAuthState.Loading -> {
                    Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
                }

                is AppAuthState.Error -> {
                    Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
                }

                is AppAuthState.Success -> {
                    Toast.makeText(this, "Bienvenido ${it.userID}", Toast.LENGTH_LONG).show()
                }
            }
        }
    }

}