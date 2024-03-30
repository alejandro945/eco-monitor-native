package com.example.ecomonitor

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.ecomonitor.databinding.ActivitySignUpBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.auth

class SignUpActivity : AppCompatActivity() {
    private val successMessage = "You have created your account!"
    private val mismatchMessage = "The passwords you've entered don't match."

    private val binding by lazy {
        ActivitySignUpBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.createAccountButton.setOnClickListener { createAccount() }
        binding.textView10.setOnClickListener { finish() }
    }

    private fun createAccount() {
        val email = binding.emailField.text.toString()
        val password = binding.passwordField.text.toString()
        val confirmPassword = binding.confirmPasswordField.text.toString()

        if (password == confirmPassword) {
            Firebase.auth.createUserWithEmailAndPassword(email, password)
                .addOnSuccessListener { showMessage(successMessage) ; finish() }
                .addOnFailureListener { showMessage(it.message!!) }
        } else { showMessage(mismatchMessage) }
    }

    private fun showMessage(text : String) {
        Toast.makeText(this, text, Toast.LENGTH_LONG).show()
    }
}