package com.example.shedula_next_try.Model

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

enum class Role {
    ADMIN,
    EMPLOYEE
}
data class CalendarEntry(
    val date: String,
    val workingHours: Double,
    val vacationDays: Int
) {
    fun toMap(): Map<String, Any> {
        return mapOf(
            "date" to date,
            "workingHours" to workingHours,
            "vacationDays" to vacationDays
        )
    }
}

class User(
    val username: String,
    val role: Role,
    var workhours: Double,
    var workedhours: Double,
    var vacationDays: Int,
    var calendarEntries: MutableList<CalendarEntry> = mutableListOf()
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
                        "vacationDays" to vacationDays,
                        "calendarEntries" to calendarEntries.map { it.toMap() }

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
                "vacationDays", updatedUser.vacationDays,
                "calendarEntries" to updatedUser.calendarEntries.map { it.toMap() }
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
suspend fun getCurrentUser(username: String): User? {
    return FirebaseAuth.getInstance().currentUser?.let { currentUser ->
        val snapshot = FirebaseFirestore.getInstance()
            .collection("users")
            .document(currentUser.uid)
            .get()
            .await()
        snapshot.toObject(User::class.java)
    }
}

suspend fun getUserFromDatabase(username: String): User? {
    val snapshot = FirebaseFirestore.getInstance()
        .collection("users")
        .document(username)
        .get()
        .await()
    return snapshot.toObject(User::class.java)
}

suspend fun updateUserInDatabase(user: User) {
    FirebaseFirestore.getInstance()
        .collection("users")
        .document(user.username)
        .set(user)
        .await()
}