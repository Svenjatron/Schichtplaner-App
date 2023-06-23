package com.example.shedula_next_try.Model

import android.content.Intent
import android.nfc.NfcAdapter
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {
    var user: User? = null
    var team: Team? = null
    var punchInTime = MutableLiveData<Long>()
    var punchOutTime = MutableLiveData<Long>()
    var hoursWorked = MutableLiveData<String>()
    private val calendarUtils = CalendarUtils()

    init {
        punchInTime.value = 0L
        punchOutTime.value = 0L
        hoursWorked.value = ""
    }

    fun createUser(
        username: String,
        password: String,
        role: Role,
        workhours: Double,
        workedhours: Double,
        vacationDays: Int
    ) {
        val newUser = User(username, role, workhours, workedhours, vacationDays)
        user = newUser
        viewModelScope.launch {
            newUser.saveUser(password)
        }
    }


    fun updateUser(updatedUser: User) {
        viewModelScope.launch {
            user?.updateUser(updatedUser)
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
            val teammate = User(username, Role.EMPLOYEE, 0.0, 0.0, 0)
            it.addTeammate(teammate)
            viewModelScope.launch {
                it.updateTeam(it)
            }
        }
    }

    fun removeTeammate(username: String) {
        team?.let {
            val teammate = User(username, Role.EMPLOYEE, 0.0, 0.0, 0)
            it.removeTeammate(teammate)
            viewModelScope.launch {
                it.updateTeam(it)
            }
        }
    }

    fun createAdmin(username: String, password: String, role: Role, workhours: Double, workedhours: Double, vacationDays: Int, teamname: String) {
        val admin = User(username = username, role = role, workhours = workhours, workedhours = workedhours, vacationDays = vacationDays)
        val team = Team(teamname = teamname, teammates = mutableListOf(admin))
        viewModelScope.launch {
            admin.saveUser(password)
            team.saveTeam()
        }
    }
    suspend fun addEntry(username: String, date: String, workingHours: Double, vacationDays: Int) {
        val currentUser = getCurrentUser(username)
        if (currentUser != null) {
            calendarUtils.addEntry(date, workingHours, vacationDays)
            updateUserInDatabase(currentUser)
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
    fun handleNfcIntent(intent: Intent?) {
        if (NfcAdapter.ACTION_NDEF_DISCOVERED == intent?.action || NfcAdapter.ACTION_TAG_DISCOVERED == intent?.action) {
            if (punchInTime.value == 0L) {
                punchInTime.value = System.currentTimeMillis()
                // ... Communicate punch in
            } else if (punchOutTime.value == 0L) {
                punchOutTime.value = System.currentTimeMillis()
                // ... Communicate punch out
                calculateTimeDifference()
            } else {
                // Reset if both times are already recorded
                punchInTime.value = 0
                punchOutTime.value = 0
                hoursWorked.value = ""
                // ... Communicate reset
            }
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
