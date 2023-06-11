package com.example.shedula_next_try.Model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {
    var user: User? = null
    var team: Team? = null

    fun createUser(username: String, password: String, role: Role, workhours: Double, workedhours: Double, vacationDays: Int) {
        val newUser = User(username, password, role, workhours, workedhours, vacationDays)
        user = newUser
        viewModelScope.launch {
            newUser.saveUser()
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
            val teammate = User(username, "", Role.EMPLOYEE, 0.0, 0.0, 0)
            it.addTeammate(teammate)
            viewModelScope.launch {
                it.updateTeam(it)
            }
        }
    }

    fun removeTeammate(username: String) {
        team?.let {
            val teammate = User(username, "", Role.EMPLOYEE, 0.0, 0.0, 0)
            it.removeTeammate(teammate)
            viewModelScope.launch {
                it.updateTeam(it)
            }
        }
    }

    fun createAdmin(admin: User, team: Team) {
        viewModelScope.launch {
            admin.saveUser()
            team.saveTeam()
        }
    }
}
