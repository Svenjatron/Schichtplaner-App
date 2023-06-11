package com.example.shedula_next_try

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.runtime.*
import androidx.navigation.NavController
import androidx.compose.material3.Switch
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.rememberScrollState
import androidx.compose.ui.focus.onFocusChanged
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.example.shedula_next_try.Model.MainViewModel
import com.example.shedula_next_try.Model.Role
import com.example.shedula_next_try.Model.User
import com.example.shedula_next_try.Model.Team

@Composable
fun AdminRegister(navController: NavController, context: Context, viewModel: MainViewModel) {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var role by remember { mutableStateOf(Role.ADMIN) }
    var workhours by remember { mutableStateOf(0.0) }
    var vacationDays by remember { mutableStateOf(0) }
    val offsetY = remember { mutableStateOf(0.dp) }
    var teamname by remember { mutableStateOf("") }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(com.example.shedula_next_try.ui.theme.unserSchwarz)
            .offset(y = offsetY.value)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .verticalScroll(enabled = true, state = rememberScrollState())
        ) {
            Text(
                text = "Teammanager und Team anlegen",
                modifier = Modifier.padding(start = 20.dp, top = 20.dp),
                fontSize = 20.sp,
                color = com.example.shedula_next_try.ui.theme.weiss
            )

            Text(
                text = "Username",
                modifier = Modifier.padding(start = 20.dp, top = 20.dp),
                fontSize = 20.sp,
                color = com.example.shedula_next_try.ui.theme.weiss
            )

            BasicTextField(
                value = username,
                onValueChange = { username = it },
                textStyle = MaterialTheme.typography.bodyLarge,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .background(
                        com.example.shedula_next_try.ui.theme.leichtesGrau,
                        shape = RoundedCornerShape(8.dp)
                    )
                    .padding(horizontal = 20.dp, vertical = 20.dp)
            )

            Text(
                text = "Passwort",
                modifier = Modifier.padding(start = 20.dp, top = 20.dp),
                fontSize = 20.sp,
                color = com.example.shedula_next_try.ui.theme.weiss
            )

            BasicTextField(
                value = password,
                onValueChange = { password = it },
                textStyle = MaterialTheme.typography.bodyLarge,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .background(
                        com.example.shedula_next_try.ui.theme.leichtesGrau,
                        shape = RoundedCornerShape(8.dp)
                    )
                    .padding(horizontal = 20.dp, vertical = 20.dp)
            )

            Text(
                text = "Arbeitsstunden",
                modifier = Modifier.padding(start = 20.dp, top = 20.dp),
                fontSize = 20.sp,
                color = com.example.shedula_next_try.ui.theme.weiss
            )

            BasicTextField(
                value = workhours.toString(),
                onValueChange = { workhours = it.toDoubleOrNull() ?: 0.0 },
                textStyle = MaterialTheme.typography.bodyLarge,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .background(
                        com.example.shedula_next_try.ui.theme.leichtesGrau,
                        shape = RoundedCornerShape(8.dp)
                    )
                    .padding(horizontal = 20.dp, vertical = 20.dp)
                    .onFocusChanged { focusState ->
                        if (focusState.isFocused) {
                            offsetY.value = (-100).dp
                        } else {
                            offsetY.value = 0.dp
                        }
                    }
            )

            Text(
                text = "Urlaubstage",
                modifier = Modifier.padding(start = 20.dp, top = 20.dp),
                fontSize = 20.sp,
                color = com.example.shedula_next_try.ui.theme.weiss
            )

            BasicTextField(
                value = vacationDays.toString(),
                onValueChange = { vacationDays = it.toIntOrNull() ?: 0 },
                textStyle = MaterialTheme.typography.bodyLarge,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .background(
                        com.example.shedula_next_try.ui.theme.leichtesGrau,
                        shape = RoundedCornerShape(8.dp)
                    )
                    .padding(horizontal = 20.dp, vertical = 20.dp)
                    .onFocusChanged { focusState ->
                        if (focusState.isFocused) {
                            offsetY.value = (-100).dp
                        } else {
                            offsetY.value = 0.dp
                        }
                    }
            )

            Text(
                text = "Teamname",
                modifier = Modifier.padding(start = 20.dp, top = 20.dp),
                fontSize = 20.sp,
                color = com.example.shedula_next_try.ui.theme.weiss
            )

            BasicTextField(
                value = teamname,
                onValueChange = { teamname = it },
                textStyle = MaterialTheme.typography.bodyLarge,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .background(
                        com.example.shedula_next_try.ui.theme.leichtesGrau,
                        shape = RoundedCornerShape(8.dp)
                    )
                    .padding(horizontal = 20.dp, vertical = 20.dp)
                    .onFocusChanged { focusState ->
                        if (focusState.isFocused) {
                            offsetY.value = (-400).dp
                        } else {
                            offsetY.value = 0.dp
                        }
                    }
            )
            Button(
                onClick = {
                    viewModel.createAdmin(username, password, Role.ADMIN, workhours, 0.0, vacationDays, teamname)
                    Toast.makeText(
                        context, "Teammanager und Team erfolgreich erstellt.",
                        Toast.LENGTH_SHORT
                    ).show()
                    navController.navigate("LoginScreen")
                },
                modifier = Modifier
                    .padding(top = 20.dp)
                    .fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = com.example.shedula_next_try.ui.theme.leichtesGrau,
                    contentColor = com.example.shedula_next_try.ui.theme.unserSchwarz
                )
            ) {
                Text(
                    text = "Team und Teammanager anlegen",
                    fontSize = 20.sp
                )
            }



            Button(
                onClick = {
                    navController.navigate("LoginScreen")
                },
                modifier = Modifier
                    .padding(top = 20.dp)
                    .fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = com.example.shedula_next_try.ui.theme.leichtesGrau,
                    contentColor = com.example.shedula_next_try.ui.theme.unserSchwarz
                )
            ) {
                Text(
                    text = "Zurück zum Login",
                    fontSize = 20.sp
                )
            }


        }
    }
}




