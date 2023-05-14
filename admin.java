class AdminManager {
    private val admins = mutableMapOf<String, String>() // Eine mutable Map, um die Admin-Benutzernamen und Passwörter zu speichern

    // Funktion zum Hinzufügen eines Admins
    fun addAdmin(username: String, password: String) {
        // Überprüfen, ob der Benutzername bereits vergeben ist
        if (admins.containsKey(username)) {
            throw IllegalArgumentException("Der Admin-Benutzername $username ist bereits vergeben.")
        }
        // Hinzufügen des Benutzernamens und des Passworts zur Map
        admins[username] = password
        // Ausgabe einer Bestätigungsmeldung
        println("Admin $username wurde hinzugefügt.")
    }

    // Funktion zum Löschen eines Admins
    fun deleteAdmin(username: String) {
        // Überprüfen, ob der Benutzername vorhanden ist
        if (!admins.containsKey(username)) {
            throw IllegalArgumentException("Der Admin-Benutzername $username existiert nicht.")
        }
        // Löschen des Benutzernamens und des Passworts aus der Map
        admins.remove(username)
        // Ausgabe einer Bestätigungsmeldung
        println("Admin $username wurde gelöscht.")
    }

    // Funktion zum Ändern des Passworts eines Admins
    fun changePassword(username: String, newPassword: String) {
        // Überprüfen, ob der Benutzername vorhanden ist
        if (!admins.containsKey(username)) {
            throw IllegalArgumentException("Der Admin-Benutzername $username existiert nicht.")
        }
        // Ändern des Passworts in der Map
        admins[username] = newPassword
        // Ausgabe einer Bestätigungsmeldung
        println("Das Passwort für Admin $username wurde geändert.")
    }
}
