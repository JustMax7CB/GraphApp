package com.maxshapira.triosoftapp.ui.registration

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.textfield.TextInputEditText
import com.maxshapira.triosoftapp.MainActivity
import com.maxshapira.triosoftapp.databinding.ActivityRegistrationBinding

class RegistrationActivity : AppCompatActivity() {

    private lateinit var usernameEditText: TextInputEditText
    private lateinit var passwordEditText: TextInputEditText
    private lateinit var signUpButton: Button

    private lateinit var registrationViewModel: RegistrationViewModel
    private lateinit var binding: ActivityRegistrationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegistrationBinding.inflate(layoutInflater)
        setContentView(binding.root)
        registrationViewModel = ViewModelProvider(this)[RegistrationViewModel::class.java]
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
        registrationViewModel.registerUser(username, password) { result, errorMessage ->
            if (result) {
                val mainIntent = Intent(this, MainActivity::class.java)
                Toast.makeText(this, "Successful Registration", Toast.LENGTH_LONG).show()
                startActivity(mainIntent)
            } else {
                Toast.makeText(this, errorMessage, Toast.LENGTH_LONG).show()
            }
        }


    }
}