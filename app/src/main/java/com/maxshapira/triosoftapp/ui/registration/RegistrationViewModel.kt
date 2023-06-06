package com.maxshapira.triosoftapp.ui.registration

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModel
import com.maxshapira.triosoftapp.network.firebase.FirebaseAuthManager

class RegistrationViewModel : ViewModel() {

    fun registerUser(username: String, password: String, context: Context) {
        FirebaseAuthManager.signUp(username, password) { success, errorMessage ->
            if (success) {
                Log.d("[TAG]", "Regstration Success!")
            } else {
                Toast.makeText(context, errorMessage, Toast.LENGTH_LONG).show()
            }
        }
    }

}