package com.example.shedula_next_try.Model

import android.util.Log
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
data class User(
    val username: String,
    val role: Role,
    val workhours: Double,
    val workedhours: Double,
    val vacationDays: Int,
    val calendarEntries: List<CalendarEntry>
) {
    fun toMap(): Map<String, Any> {
        return mapOf(
            "username" to username,
            "role" to role.toString(),
            "workhours" to workhours,
            "workedhours" to workedhours,
            "vacationDays" to vacationDays,
            "calendarEntries" to calendarEntries.map { it.toMap() }
        )
    }

    // Fake Email Ersteller
    val email: String = "$username@shedula.com"

    private val db = FirebaseFirestore.getInstance()

    fun saveUser(password: String, calendarEntries: List<CalendarEntry>) {
        Log.d(TAG, "saveUser: Start saving user - Username: $username")

        FirebaseAuth.getInstance().createUserWithEmailAndPassword(this.email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d(TAG, "saveUser: Firebase Auth user created successfully")

                    val userMap = toMap()
                    db.collection("users")
                        .document(username)
                        .set(userMap)
                        .addOnSuccessListener {
                            Log.d(TAG, "saveUser: User data saved successfully to Firestore")
                        }
                        .addOnFailureListener { e ->
                            Log.e(TAG, "saveUser: Error saving user data to Firestore", e)
                        }
                } else {
                    Log.e(TAG, "saveUser: Error creating user in Firebase Auth", task.exception)
                }
            }
    }

    suspend fun updateUser(updatedUser: User) {
        Log.d(TAG, "updateUser: Start updating user data for username: ${updatedUser.username}")

        db.collection("users")
            .document(username)
            .set(updatedUser.toMap())
            .await()

        Log.d(TAG, "updateUser: User data updated successfully")
    }

    fun deleteUser() {
        FirebaseAuth.getInstance().currentUser?.delete()
            ?.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d(TAG, "deleteUser: Firebase Auth user deleted successfully")

                    db.collection("users")
                        .document(username)
                        .delete()
                        .addOnSuccessListener {
                            Log.d(TAG, "deleteUser: User data deleted successfully from Firestore")
                        }
                        .addOnFailureListener { e ->
                            Log.e(TAG, "deleteUser: Error deleting user data from Firestore", e)
                        }
                } else {
                    Log.e(TAG, "deleteUser: Error deleting user in Firebase Auth", task.exception)
                }
            }
    }

    companion object {
        private const val TAG = "UserClass"

        suspend fun getCurrentUser(username: String): User? {
            Log.d(TAG, "getCurrentUser: Start fetching user data for username: $username")

            val currentUser = FirebaseAuth.getInstance().currentUser

            if (currentUser == null) {
                Log.d(TAG, "getCurrentUser: Current user is null")
                return null
            }

            Log.d(TAG, "getCurrentUser: Current user UID: ${currentUser.uid}")

            val snapshot = FirebaseFirestore.getInstance()
                .collection("users")
                .document(currentUser.uid)
                .get()
                .await()

            val user = snapshot.toObject(User::class.java)
            if (user != null) {
                Log.d(TAG, "getCurrentUser: User data fetched successfully: $user")
            } else {
                Log.d(TAG, "getCurrentUser: User data is null")
            }

            return user
        }


        suspend fun updateUserInDatabase(updatedUser: User) {
            Log.d(TAG, "updateUserInDatabase: Start updating user data for username: ${updatedUser.username}")

            FirebaseFirestore.getInstance()
                .collection("users")
                .document(updatedUser.username)
                .set(updatedUser.toMap())
                .addOnSuccessListener {
                    Log.d(TAG, "updateUserInDatabase: User data updated successfully")
                }
                .addOnFailureListener { e ->
                    Log.e(TAG, "updateUserInDatabase: Error updating user data", e)
                }
                .await()

            Log.d(TAG, "updateUserInDatabase: Data update process completed")
        }
    }
    suspend fun addEntryToFirestore(date: String, workingHours: Double, vacationDays: Int) {
        Log.d(TAG, "addEntryToFirestore: Adding entry to Firestore - Date: $date, Working Hours: $workingHours, Vacation Days: $vacationDays")

        val updatedCalendarEntries = calendarEntries.toMutableList()
        updatedCalendarEntries.add(CalendarEntry(date, workingHours, vacationDays))
        Log.d(TAG, "addEntryToFirestore: Updated Calendar Entries: $updatedCalendarEntries")

        val updatedUser = this.copy(calendarEntries = updatedCalendarEntries)
        Log.d(TAG, "addEntryToFirestore: Updated User: $updatedUser")

        try {
            Log.d(TAG, "addEntryToFirestore: Calling updateUserInDatabase")
            updateUserInDatabase(updatedUser)
            Log.d(TAG, "addEntryToFirestore: updateUserInDatabase called successfully")
        } catch (e: Exception) {
            Log.e(TAG, "addEntryToFirestore: Error updating user in database", e)
        }

        Log.d(TAG, "addEntryToFirestore: Updated user sent to database")
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