package com.example.shedula_next_try.Model

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {
    private val TAG = "MainViewModel"
    var user: User? = null
    var team: Team? = null
    private val calendarUtils = CalendarUtils()

    var punchInTime = MutableLiveData<Long>(0)
    var punchOutTime = MutableLiveData<Long>(0)
    var hoursWorked = MutableLiveData<String>("")

    fun createUser(
        username: String,
        password: String,
        role: Role,
        workhours: Double,
        workedhours: Double,
        vacationDays: Int
    ) {
        val newUser = User(
            username = username,
            role = role,
            workhours = workhours,
            workedhours = workedhours,
            vacationDays = vacationDays,
            calendarEntries = emptyList()
        )
        user = newUser
        viewModelScope.launch {
            newUser.saveUser(password, emptyList())
        }
    }

    fun updateUser(updatedUser: User) {
        viewModelScope.launch {
            updatedUser.updateUser(updatedUser) // Aufruf über die Instanzmethode
        }
    }

    fun deleteUser() {
        viewModelScope.launch {
            user?.deleteUser()
        }
    }

    fun createTeam(teamname: String, teammates: MutableList<User>) {
        val newTeam = Team(teamname, teammates)
        team = newTeam
        viewModelScope.launch {
            newTeam.saveTeam()
        }
    }

    fun updateTeam(updatedTeam: Team) {
        viewModelScope.launch {
            team?.updateTeam(updatedTeam)
        }
    }

    fun deleteTeam() {
        viewModelScope.launch {
            team?.deleteTeam()
        }
    }

    fun addTeammate(username: String) {
        team?.let {
            val teammate = User(username, Role.EMPLOYEE, 0.0, 0.0, 0, emptyList())
            it.addTeammate(teammate)
            viewModelScope.launch {
                it.updateTeam(it)
            }
        }
    }

    fun removeTeammate(username: String) {
        team?.let {
            val teammate = User(username, Role.EMPLOYEE, 0.0, 0.0, 0, emptyList())
            it.removeTeammate(teammate)
            viewModelScope.launch {
                it.updateTeam(it)
            }
        }
    }

    fun createAdmin(
        username: String,
        password: String,
        role: Role,
        workhours: Double,
        workedhours: Double,
        vacationDays: Int,
        teamname: String
    ) {
        val admin = User(
            username = username,
            role = role,
            workhours = workhours,
            workedhours = workedhours,
            vacationDays = vacationDays,
            calendarEntries = emptyList()
        )
        val team = Team(teamname = teamname, teammates = mutableListOf(admin))
        viewModelScope.launch {
            admin.saveUser(password, emptyList())
            team.saveTeam()
        }
    }

    suspend fun addEntry(username: String, date: String, workingHours: Double, vacationDays: Int) {
        Log.d(TAG, "addEntry: Start adding entry for username: $username, date: $date")

        val currentUser = User.getCurrentUser()
        if (currentUser != null) {
            val updatedCalendarEntries = currentUser.calendarEntries.toMutableList()
            updatedCalendarEntries.add(CalendarEntry(date, workingHours, vacationDays))
            val updatedUser = currentUser.copy(calendarEntries = updatedCalendarEntries)

            Log.d(TAG, "addEntry: Updated user object: $updatedUser")

            val currentUid = FirebaseAuth.getInstance().currentUser?.uid
            if (currentUid != null) {
                User.updateUserInDatabase(currentUid, updatedUser)
            }

            // Update entry in calendarUtils
            calendarUtils.addEntry(date, workingHours, vacationDays)

            Log.d(TAG, "addEntry: Entry added successfully")
        }
    }


    fun deleteEntry(date: String, entry: CalendarEntry) {
        calendarUtils.deleteEntry(date, entry)
    }

    fun getEntries(date: String): List<CalendarEntry> {
        return calendarUtils.getEntries(date)
    }

    fun getAllEntries(): List<CalendarEntry> {
        return calendarUtils.getAllEntries()
    }

    fun clearEntries() {
        calendarUtils.clearEntries()
    }

    suspend fun addEntryToFirestore(date: String, workingHours: Double, vacationDays: Int) {
        val currentUser = User.getCurrentUser()
        Log.d(TAG, "addEntryToFirestore: currentUser: $currentUser")
        currentUser?.addEntryToFirestore(date, workingHours, vacationDays)
    }

    fun handleNfcTagScanned() {
        Log.d("NFC", "Handling NFC tag in ViewModel")

        if (punchInTime.value == 0L) {
            punchInTime.value = System.currentTimeMillis()
            Log.d("NFC", "Punch in time recorded: ${punchInTime.value}")
        } else if (punchOutTime.value == 0L) {
            punchOutTime.value = System.currentTimeMillis()
            Log.d("NFC", "Punch out time recorded: ${punchOutTime.value}")
            calculateTimeDifference()
        } else {
            punchInTime.value = 0
            punchOutTime.value = 0
            hoursWorked.value = ""
            Log.d("NFC", "Times reset")
        }
    }

    private fun calculateTimeDifference() {
        val diff = punchOutTime.value!! - punchInTime.value!!

        var seconds = diff / 1000
        var minutes = seconds / 60
        val hours = minutes / 60

        minutes %= 60
        seconds %= 60

        hoursWorked.value = "$hours:$minutes"
    }
}





