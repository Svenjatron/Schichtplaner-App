package com.example.shedula_next_try

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.runtime.*
import androidx.compose.ui.focus.onFocusChanged
import androidx.navigation.NavController
import androidx.compose.material3.IconButton


@Composable
fun LoginManager(navController: NavController) {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val usernameState = remember { mutableStateOf("") }
    val passwordState = remember { mutableStateOf("") }
    val userDoesNotExist = remember { mutableStateOf(false) }
    val offsetY = remember { mutableStateOf(0.dp) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .offset(y = offsetY.value)
    )
    {

        Column(

            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {

            // Username Einblende Textfeld
            Text(
                text = "Username",
                modifier = Modifier.padding(start = 20.dp, top = 20.dp),
                fontSize = 20.sp
            )

            // Username eingeben Textfeld
            BasicTextField(
                value = username,
                onValueChange = { username = it },
                textStyle = MaterialTheme.typography.bodyLarge,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .background(leichtesGrau, shape = RoundedCornerShape(8.dp))
                    .padding(horizontal = 20.dp, vertical = 20.dp)
            )

            // Password Einblende Textfeld
            Text(
                text = "Passwort",
                modifier = Modifier.padding(start = 20.dp, top = 20.dp),
                fontSize = 20.sp
            )

            // Password eingeben Textfeld
            BasicTextField(
                value = password,
                onValueChange = { password = it },
                textStyle = MaterialTheme.typography.bodyLarge,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .background(leichtesGrau, shape = RoundedCornerShape(8.dp))
                    .padding(horizontal = 20.dp, vertical = 20.dp)
                    .onFocusChanged { focusState ->
                        if (focusState.isFocused) {
                            offsetY.value = (-150).dp // Move the screen down by 80 pixels
                        } else {
                            offsetY.value = 0.dp // Reset the offset when focus is lost
                        }
                    }
            )

            Spacer(modifier = Modifier.height(32.dp))

            // Login Button mit Admin, Employee Splitter
            Button(
                onClick = {
                    val user = Authenticator_function(username, password)
                    if (user != null) {
                        when (user.role) {
                            Role.ADMIN -> navController.navigate("TeamManager")
                            Role.EMPLOYEE -> navController.navigate("EmployeeScreen")
                            else -> userDoesNotExist.value = true
                        }
                    } else {
                        userDoesNotExist.value = true
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 20.dp, start = 20.dp, end = 20.dp)
                    .height(100.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = com.example.shedula_next_try.ui.theme.unserOcker,
                    contentColor = unserSchwarz
                )
            ) {
                Text(
                    text = "Anmelden",
                    fontSize = 40.sp
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            // Home Button
            Button(
                onClick = {
                    navController.navigate("LoginScreen")
                },
                modifier = Modifier
                    .width(120.dp)
                    .height(50.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = com.example.shedula_next_try.ui.theme.leichtesGrau,
                    contentColor = unserSchwarz
                )
            ) {
                Text(
                    text = "Home",
                    fontSize = 16.sp
                )
            }

            // User nicht da
            if (userDoesNotExist.value) {
                Text(
                    text = "Username oder Passwort falsch!",
                    color = com.example.shedula_next_try.ui.theme.orange1,
                    fontSize = 20.sp,
                    modifier = Modifier
                        .padding(start = 20.dp, top = 8.dp, bottom = 60.dp)

                )
            }
        }
    }
}
