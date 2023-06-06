package com.maxshapira.triosoftapp.ui.login


import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.maxshapira.triosoftapp.network.firebase.FirebaseAuthManager

class LoginViewModel : ViewModel() {
    val loginResultLiveData: MutableLiveData<LoginResult> = MutableLiveData()

    fun loginUser(username: String, password: String, context: Context) {
        FirebaseAuthManager.signIn(username, password) { success, errorMessage ->
            if (success) {
                Log.d("[TAG]", "Login Success")
                loginResultLiveData.postValue(LoginResult.Success)
            } else {
                Toast.makeText(context, errorMessage, Toast.LENGTH_LONG).show()
                loginResultLiveData.postValue(LoginResult.Error(errorMessage ?: "Unknown error"))
            }
        }
    }

    sealed class LoginResult {
        object Success : LoginResult()
        data class Error(val errorMessage: String) : LoginResult()
    }
}

