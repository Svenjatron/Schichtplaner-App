package com.example.shedula_next_try.Model

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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

        val currentUser = User.getCurrentUser(username)
        if (currentUser != null) {
            val updatedCalendarEntries = currentUser.calendarEntries.toMutableList()
            updatedCalendarEntries.add(CalendarEntry(date, workingHours, vacationDays))
            val updatedUser = currentUser.copy(calendarEntries = updatedCalendarEntries)

            Log.d(TAG, "addEntry: Updated user object: $updatedUser")

            User.updateUserInDatabase(updatedUser)

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

    fun updatePunchInAndOutTimes(punchIn: Long, punchOut: Long) {
        punchInTime.postValue(punchIn)
        punchOutTime.postValue(punchOut)

        val hoursWorked = (punchOut - punchIn) / (1000 * 60 * 60)
        this.hoursWorked.postValue(hoursWorked.toString())
    }
    suspend fun addEntryToFirestore(username: String, date: String, workingHours: Double, vacationDays: Int) {
        Log.d(TAG, "addEntryToFirestore: Passed username: $username")

        val currentUser = User.getCurrentUser(username)
        Log.d(TAG, "addEntryToFirestore: currentUser: $currentUser")

        currentUser?.addEntryToFirestore(date, workingHours, vacationDays)
    }

}
