package com.example.shedula_next_try.Model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {
    var user: User? = null
    var team: Team? = null

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
}
