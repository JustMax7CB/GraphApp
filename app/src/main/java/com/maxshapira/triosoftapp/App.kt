package com.maxshapira.triosoftapp

import android.app.Application
import android.content.Intent
import com.google.firebase.FirebaseApp
import com.maxshapira.triosoftapp.data.local.db.AppDatabase

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        FirebaseApp.initializeApp(this)
        AppDatabase.getInstance(this)

        val mainIntent = Intent(this, MainActivity::class.java)
        mainIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(mainIntent)

    }
}