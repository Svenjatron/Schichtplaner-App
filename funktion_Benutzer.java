class UserManager {
    private val users = mutableMapOf<String, String>() // Hier wird eine mutable Map initialisiert, um Benutzername und Passwort zu speichern

    // Funktion zum Hinzufügen eines Benutzers
    fun addUser(username: String, password: String) {
        // Überprüfen, ob der Benutzername bereits vergeben ist
        if (users.containsKey(username)) {
            throw IllegalArgumentException("Der Benutzername $username ist bereits vergeben.")
        }
        // Hinzufügen des Benutzernamens und des Passworts zur Map
        users[username] = password
        // Ausgabe einer Bestätigungsmeldung
        println("Benutzer $username wurde hinzugefügt.")
    }

    // Funktion zum Löschen eines Benutzers
    fun deleteUser(username: String) {
        // Überprüfen, ob der Benutzername vorhanden ist
        if (!users.containsKey(username)) {
            throw IllegalArgumentException("Der Benutzername $username existiert nicht.")
        }
        // Löschen des Benutzernamens und des Passworts aus der Map
        users.remove(username)
        // Ausgabe einer Bestätigungsmeldung
        println("Benutzer $username wurde gelöscht.")
    }

    // Funktion zum Ändern des Passworts eines Benutzers
    fun changePassword(username: String, newPassword: String) {
        // Überprüfen, ob der Benutzername vorhanden ist
        if (!users.containsKey(username)) {
            throw IllegalArgumentException("Der Benutzername $username existiert nicht.")
        }
        // Ändern des Passworts in der Map
        users[username] = newPassword
        // Ausgabe einer Bestätigungsmeldung
        println("Das Passwort für Benutzer $username wurde geändert.")
    }
}
 