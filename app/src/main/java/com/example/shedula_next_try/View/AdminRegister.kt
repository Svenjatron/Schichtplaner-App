package com.example.shedula_next_try

import Role
import User
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
import androidx.compose.material3.DropdownMenu
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.runtime.*
import androidx.navigation.NavController
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.ui.platform.LocalContext
import androidx.compose.material3.Switch
import androidx.compose.ui.focus.onFocusChanged

@Composable
fun AdminRegister(navController: NavController) {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var role by remember { mutableStateOf(Role.ADMIN) }
    var workhours by remember { mutableStateOf(0.0) }
    var vacationDays by remember { mutableStateOf(0) }
    val offsetY = remember { mutableStateOf(0.dp) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(com.example.shedula_next_try.ui.theme.unserSchwarz)
            .offset(y = offsetY.value)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Admin anlegen",
                modifier = Modifier.padding(start = 20.dp, top = 20.dp),
                fontSize = 30.sp,
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
                onValueChange = { value ->
                    workhours = value.toDoubleOrNull() ?: 0.0
                },
                textStyle = MaterialTheme.typography.bodyLarge,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .onFocusChanged { focusState ->
                        if (focusState.isFocused) {
                            offsetY.value = (-260).dp
                        } else {
                            offsetY.value = 0.dp
                        }
                    }
                    .background(
                        com.example.shedula_next_try.ui.theme.leichtesGrau,
                        shape = RoundedCornerShape(8.dp)
                    )
                    .padding(horizontal = 20.dp, vertical = 20.dp)
            )

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
                    .clickable {
                        role = if (role == Role.ADMIN) Role.EMPLOYEE else Role.ADMIN
                    },
                contentAlignment = Alignment.Center
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Admin",
                        fontSize = 16.sp,
                        color = com.example.shedula_next_try.ui.theme.weiss,
                        modifier = Modifier.padding(end = 8.dp)
                    )

                    Switch(
                        checked = role == Role.EMPLOYEE,
                        onCheckedChange = {
                            role = if (it) Role.EMPLOYEE else Role.ADMIN
                        }
                    )

                    Text(
                        text = "Angestellte:r",
                        fontSize = 16.sp,
                        color = com.example.shedula_next_try.ui.theme.weiss,
                        modifier = Modifier.padding(start = 8.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            Button(
                onClick = {

                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp, start = 20.dp, end = 20.dp)
                    .height(100.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = com.example.shedula_next_try.ui.theme.leichtesGrau,
                    contentColor = com.example.shedula_next_try.ui.theme.unserSchwarz
                ),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text(
                    text = "Teammanager anlegen",
                    fontSize = 20.sp
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            Button(
                onClick = {
                    navController.navigate("LoginScreen")
                },
                modifier = Modifier
                    .width(180.dp)
                    .height(50.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = com.example.shedula_next_try.ui.theme.leichtesGrau,
                    contentColor = com.example.shedula_next_try.ui.theme.unserSchwarz
                )
            ) {
                Text(
                    text = "Zurück",
                    fontSize = 16.sp
                )
            }
        }
    }
}





