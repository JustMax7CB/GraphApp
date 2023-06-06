package com.maxshapira.triosoftapp.network.firebase

import com.google.firebase.auth.FirebaseAuth

object FirebaseAuthManager {

    private val firebaseAuth = FirebaseAuth.getInstance()

    fun signIn(username: String, password: String, onComplete: (Boolean, String?) -> Unit) {
        firebaseAuth.signInWithEmailAndPassword(username, password)
            .addOnCompleteListener { signInTask ->
                if (signInTask.isSuccessful) {
                    onComplete(true, null)
                } else {
                    val errorMessage = signInTask.exception?.message
                    onComplete(false, errorMessage)
                }
            }
    }


    fun signUp(username: String, password: String, onComplete: (Boolean, String?) -> Unit) =
        firebaseAuth.createUserWithEmailAndPassword(username, password)
            .addOnCompleteListener { signUpTask ->
                if (signUpTask.isSuccessful) {
                    onComplete(true, null)
                } else {
                    val errorMessage = signUpTask.exception?.message
                    onComplete(false, errorMessage)
                }
            }

    fun signOut() = firebaseAuth.signOut()


    fun getCurrentUser() = firebaseAuth.currentUser

    fun getUserId() = firebaseAuth.currentUser?.uid


}