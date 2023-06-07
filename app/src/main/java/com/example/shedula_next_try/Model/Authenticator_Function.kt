package com.example.shedula_next_try.Model
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

fun Authenticator_function(username: String, password: String, onComplete: (FirebaseUser?) -> Unit) {
    val auth = FirebaseAuth.getInstance()

    auth.signInWithEmailAndPassword(username, password)
        .addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val user = auth.currentUser
                onComplete(user)
            } else {
                onComplete(null)
            }
        }
}

