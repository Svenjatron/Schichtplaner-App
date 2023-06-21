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
    val role: Role,
    var workhours: Double,
    var workedhours: Double,
    var vacationDays: Int
) {
    // Fake Email Ersteller
    val email: String = "$username@shedula.com"

    private val db = FirebaseFirestore.getInstance()

    fun saveUser(password: String) {
        // Benutzer in Firebase Auth
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(this.email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // Benutzer in Firestore
                    val userMap = mapOf(
                        "username" to username,
                        "role" to role.toString(),
                        "workhours" to workhours,
                        "workedhours" to workedhours,
                        "vacationDays" to vacationDays
                    )
                    db.collection("users")
                        .document(username)
                        .set(userMap)
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
                "role", updatedUser.role,
                "workhours", updatedUser.workhours,
                "workedhours", updatedUser.workedhours,
                "vacationDays", updatedUser.vacationDays
            )
    }

    fun deleteUser() {
        FirebaseAuth.getInstance().currentUser?.delete()
            ?.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // Der Benutzer wurde erfolgreich aus Firebase Auth gelöscht
                    // Jetzt löschen wir den Benutzer aus Firestore
                    db.collection("users")
                        .document(username)
                        .delete()
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
