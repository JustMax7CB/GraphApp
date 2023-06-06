package com.maxshapira.triosoftapp.ui.login

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.textfield.TextInputEditText
import com.maxshapira.triosoftapp.MainActivity
import com.maxshapira.triosoftapp.databinding.ActivityLoginBinding
import com.maxshapira.triosoftapp.ui.registration.RegistrationActivity

class LoginActivity : AppCompatActivity() {

    private lateinit var usernameEditText: TextInputEditText
    private lateinit var passwordEditText: TextInputEditText
    private lateinit var signInButton: Button
    private lateinit var signUpButton: Button

    private lateinit var binding: ActivityLoginBinding
    private lateinit var loginViewModel: LoginViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        bindWidgets()
        setClickListeners()
    }

    private fun bindWidgets() {
        usernameEditText = binding.editTextLoginUserName
        passwordEditText = binding.editTextLoginUserPassword
        signInButton = binding.buttonSignIn
        signUpButton = binding.buttonLoginSignUp
    }

    private fun setClickListeners() {
        signInButton.setOnClickListener { signIn() }
        signUpButton.setOnClickListener { navigateToRegistration() }
    }

    private fun navigateToRegistration() {
        val registrationActivityIntent = Intent(this, RegistrationActivity::class.java)
        startActivity(registrationActivityIntent)
    }

    private fun signIn() {
        val username = usernameEditText.text.toString()
        val password = passwordEditText.text.toString()
        loginViewModel = ViewModelProvider(this)[LoginViewModel::class.java]
        loginViewModel.loginUser(username, password, this)
        loginViewModel.loginResultLiveData.observe(this) { loginResult ->
            when (loginResult) {
                is LoginViewModel.LoginResult.Success -> {
                    val mainIntent = Intent(this, MainActivity::class.java)
                    startActivity(mainIntent)
                }

                is LoginViewModel.LoginResult.Error -> {
                    println("Error logging in")
                }
            }
        }

    }


}