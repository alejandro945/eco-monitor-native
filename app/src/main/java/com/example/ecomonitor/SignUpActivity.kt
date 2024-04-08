package com.example.ecomonitor

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.ecomonitor.databinding.ActivitySignUpBinding

class SignUpActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivitySignUpBinding.inflate(layoutInflater)
    }

    // Space for viewmodel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        // binding.signupButton listener

        // binding.loginButton listener

        // binding.googleSignUp listener

        // binding.appleSignUp listener
    }
}