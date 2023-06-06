package com.maxshapira.triosoftapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.google.firebase.auth.FirebaseAuth
import com.maxshapira.triosoftapp.ui.home.HomeActivity
import com.maxshapira.triosoftapp.ui.login.LoginActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)


        // Check if the user is logged in
        val currentUser = FirebaseAuth.getInstance().currentUser

        // Determine the destination activity based on the login status
        val destinationActivity = if (currentUser != null) {
            Log.d("[Route]", "Current user: $currentUser")
            HomeActivity::class.java
        } else {
            LoginActivity::class.java
        }

        // Start the appropriate activity
        val intent = Intent(applicationContext, destinationActivity)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
    }
}