package com.example.shedula_next_try.Model

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
fun Authenticator_function(username: String, password: String, onComplete: (FirebaseUser?) -> Unit) {
    val auth = FirebaseAuth.getInstance()

    // Benutzername in Fake-Email umwandeln
    val email = "$username@meineapp.com"

    auth.signInWithEmailAndPassword(email, password)
        .addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val user = auth.currentUser
                onComplete(user)
            } else {
                onComplete(null)
            }
        }
}
