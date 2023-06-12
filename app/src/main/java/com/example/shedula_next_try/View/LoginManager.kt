package com.example.shedula_next_try.View

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.focus.onFocusChanged
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.shedula_next_try.Model.Authenticator_function
import com.example.shedula_next_try.Model.LocalNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

@Composable
fun LoginManager() {
    val navController = LocalNavController.current
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val userDoesNotExist = remember { mutableStateOf(false) }
    val offsetY = remember { mutableStateOf(0.dp) }

    var user by remember { mutableStateOf<FirebaseUser?>(null) }
    val auth = FirebaseAuth.getInstance()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .offset(y = offsetY.value)
    ) {
        Box(
            modifier = Modifier
                .height(50.dp)
                .fillMaxWidth()
                .padding(start = 20.dp, top = 20.dp, end = 20.dp)
                .background(
                    brush = Brush.horizontalGradient(
                        colors = listOf(com.example.shedula_next_try.ui.theme.orange2, com.example.shedula_next_try.ui.theme.unserOcker),
                        startX = 0f,
                        endX = 500f
                    )
                )
        )

        Box(
            modifier = Modifier
                .height(50.dp)
                .fillMaxWidth()
                .padding(start = 20.dp, bottom = 20.dp, end = 20.dp)
                .background(
                    brush = Brush.horizontalGradient(
                        colors = listOf(com.example.shedula_next_try.ui.theme.unserOcker, com.example.shedula_next_try.ui.theme.orange2),
                        startX = 0f,
                        endX = 500f
                    )
                )
                .align(Alignment.BottomCenter)
        )

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
                    Authenticator_function(username, password) { firebaseUser ->
                        user = firebaseUser
                        if (firebaseUser != null) {
                            when (firebaseUser.email) {
                                // Dieser Teil ist noch Bullshit
                                "admin@example.com" -> navController.navigate("AdminScreen")
                                else -> navController.navigate("EmployeeScreen")
                            }
                        } else {
                            userDoesNotExist.value = true
                        }
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
            Spacer(modifier = Modifier.height(20.dp))

            // Home Button
            Button(
                onClick = {
                    navController.navigate("LoginScreen")
                },
                modifier = Modifier
                    .width(180.dp)
                    .height(50.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = com.example.shedula_next_try.ui.theme.leichtesGrau,
                    contentColor = unserSchwarz
                )
            ) {
                Text(
                    text = "Zurück",
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
