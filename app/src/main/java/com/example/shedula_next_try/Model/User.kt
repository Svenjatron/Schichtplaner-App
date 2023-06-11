package com.example.shedula_next_try.Model

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import android.content.Context

enum class Role {
    ADMIN,
    EMPLOYEE
}

class User(
    val username: String,
    val password: String,
    val role: Role,
    var workhours: Double,
    var workedhours: Double,
    var vacationDays: Int
) {
    // Fake Email Ersteller
    val email: String = "$username@meineapp.com"

    private val db = FirebaseFirestore.getInstance()

    suspend fun saveUser() {
        db.collection("users")
            .document(username)
            .set(this)
        // Erstelle den Benutzer in Firebase Auth
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(this.email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // Der Benutzer wurde erfolgreich in Firebase Auth erstellt
                } else {
                    // Es gab einen Fehler beim Erstellen des Benutzers in Firebase Auth
                }
            }
    }

    suspend fun updateUser(updatedUser: User) {
        db.collection("users")
            .document(username)
            .update(
                "username", updatedUser.username,
                "password", updatedUser.password,
                "role", updatedUser.role,
                "workhours", updatedUser.workhours,
                "workedhours", updatedUser.workedhours,
                "vacationDays", updatedUser.vacationDays
            )
    }

    suspend fun deleteUser() {
        db.collection("users")
            .document(username)
            .delete()
        FirebaseAuth.getInstance().currentUser?.delete()
            ?.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // Der Benutzer wurde erfolgreich aus Firebase Auth gelöscht
                }
            }
    }
}

class Team(val teamname: String, var teammates: MutableList<User>) {
    private val db = FirebaseFirestore.getInstance()

    suspend fun saveTeam() {
        db.collection("teams")
            .document(teamname)
            .set(this)
    }

    suspend fun updateTeam(updatedTeam: Team) {
        db.collection("teams")
            .document(teamname)
            .update(
                "teammates", updatedTeam.teammates
            )
    }

    suspend fun deleteTeam() {
        db.collection("teams")
            .document(teamname)
            .delete()
    }

    fun addTeammate(teammate: User) {
        teammates.add(teammate)
    }

    fun removeTeammate(teammate: User) {
        teammates.remove(teammate)
    }
}
