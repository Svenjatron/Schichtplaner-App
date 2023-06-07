import com.google.firebase.firestore.FirebaseFirestore
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
    private val db = FirebaseFirestore.getInstance()

    fun saveUser(context: Context) {
        db.collection("users")
            .document(username)
            .set(this)
            .addOnSuccessListener {
                // Textbox einfügen / Success
            }
            .addOnFailureListener { exception ->
                // Textbox einfügen / Fehler
            }
    }

    fun updateUser(context: Context, updatedUser: User) {
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
            .addOnSuccessListener {
                // Textbox einfügen / Success
            }
            .addOnFailureListener { exception ->
                // Textbox einfügen / Fehler
            }
    }

    fun deleteUser() {
        db.collection("users")
            .document(username)
            .delete()
            .addOnSuccessListener {
                // Textbox einfügen / Success
            }
            .addOnFailureListener { exception ->
                // Textbox einfügen / Fehler
            }
    }
}
