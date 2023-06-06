package com.maxshapira.triosoftapp.ui.registration

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText
import com.maxshapira.triosoftapp.databinding.ActivityRegistrationBinding

class RegistrationActivity : AppCompatActivity() {

    private lateinit var usernameEditText: TextInputEditText
    private lateinit var passwordEditText: TextInputEditText
    private lateinit var signUpButton: Button

    private val registrationViewModel = RegistrationViewModel()
    private lateinit var binding: ActivityRegistrationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegistrationBinding.inflate(layoutInflater)
        setContentView(binding.root)
        bindWidgets()
        setClickListener()
    }

    private fun bindWidgets() {
        usernameEditText = binding.editTextRegisterUserName
        passwordEditText = binding.editTextRegisterUserPassword
        signUpButton = binding.buttonRegisterSignUp
    }

    private fun setClickListener() {
        signUpButton.setOnClickListener { signUp() }
    }

    private fun signUp() {
        val username = usernameEditText.text.toString().trim()
        val password = passwordEditText.text.toString()
        registrationViewModel.registerUser(username, password, this)
    }
}