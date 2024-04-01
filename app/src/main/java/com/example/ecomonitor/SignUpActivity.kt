package com.example.ecomonitor

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts.StartActivityForResult
import com.example.ecomonitor.databinding.ActivitySignUpBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.Firebase
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.auth

class SignUpActivity : AppCompatActivity() {
    private val binding by lazy { ActivitySignUpBinding.inflate(layoutInflater) }

    private val googleIntent by lazy { createGoogleIntent() }

    private fun createGoogleIntent(): Intent {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .requestIdToken(SERVER_CLIENT_ID)
            .build()
        return GoogleSignIn.getClient(this, gso).signInIntent
    }

    private lateinit var googleSignInLauncher : ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.createAccountButton.setOnClickListener { emailSignUp() }
        binding.googleSignUpButton.setOnClickListener { googleSignUp() }
        binding.textView10.setOnClickListener { finish() }

        googleSignInLauncher = registerForActivityResult(StartActivityForResult(), ::onGoogleSignUp)
    }

    private fun emailSignUp() {
        val email = binding.emailField.text.toString()
        val password = binding.passwordField.text.toString()
        val confirmPassword = binding.confirmPasswordField.text.toString()

        if (password == confirmPassword) {
            Firebase.auth.createUserWithEmailAndPassword(email, password)
                .addOnSuccessListener { showMessage(SUCCESS_MESSAGE) ; finish() }
                .addOnFailureListener { showMessage(it.message!!) }
        } else {
            showMessage(MISMATCH_MESSAGE)
        }
    }

    private fun googleSignUp() {
        googleSignInLauncher.launch(googleIntent)
    }

    private fun onGoogleSignUp(result : ActivityResult) {
        val account = GoogleSignIn.getSignedInAccountFromIntent(result.data).result
        val credential = GoogleAuthProvider.getCredential(account.idToken, null)
        Firebase.auth.signInWithCredential(credential)
            .addOnSuccessListener { showMessage(SUCCESS_MESSAGE) ; finish() } //TODO - We might want to redirect the user to the main menu of the application.
            .addOnFailureListener { showMessage(it.message!!) }
    }

    private fun showMessage(text : String) {
        Toast.makeText(this, text, Toast.LENGTH_LONG).show()
    }

    companion object {
        private const val SERVER_CLIENT_ID = "390179189698-rjd3v42it2rv1sd8dvruap6jh5pspnih.apps.googleusercontent.com"
        private const val SUCCESS_MESSAGE = "You have created your account!"
        private const val MISMATCH_MESSAGE = "The passwords you've entered don't match."
    }
}