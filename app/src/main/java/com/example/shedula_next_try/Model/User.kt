package com.example.shedula_next_try.Model

enum class Role {
    ADMIN,
    EMPLOYEE
}

class User(val username: String, val password: String, val role: Role, val workhours : Double){

}
