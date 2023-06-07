package com.maxshapira.triosoftapp.ui.registration

import android.util.Log
import androidx.lifecycle.ViewModel
import com.maxshapira.triosoftapp.network.firebase.FirebaseAuthManager

class RegistrationViewModel() : ViewModel() {


    fun registerUser(
        username: String,
        password: String,
        callback: (Boolean, String?) -> Unit
    ) {
        FirebaseAuthManager.signUp(username, password) { success, errorMessage ->
            if (success) {
                Log.d("[TAG]", "Registration Success!")
                callback(true, null)
            } else {
                callback(false, errorMessage)
            }
        }
    }

}