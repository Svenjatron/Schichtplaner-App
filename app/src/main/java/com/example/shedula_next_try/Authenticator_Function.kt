package com.example.shedula_next_try

fun Authenticator_function(username: String, password: String): User? {
    val users = listOf(
        User("Peter", "123456", com.example.shedula_next_try.Role.ADMIN, 40.0),
        User("Petra", "654321", com.example.shedula_next_try.Role.EMPLOYEE, 35.0)
    )

    return users.find { it.username == username && it.password == password }
}

