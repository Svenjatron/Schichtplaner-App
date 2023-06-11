package com.example.shedula_next_try.View

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
import androidx.navigation.compose.rememberNavController
import com.example.shedula_next_try.Model.Role
import com.example.shedula_next_try.Model.User
import com.example.shedula_next_try.Model.Team
import com.example.shedula_next_try.Model.MainViewModel

@Composable
fun TeamVerwaltung(navController: NavController, team: Team, context: Context, viewModel: MainViewModel) {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var role by remember { mutableStateOf(Role.EMPLOYEE) }
    var workhours by remember { mutableStateOf(0.0) }
    var vacationDays by remember { mutableStateOf(0) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(enabled = true, state = rememberScrollState())
    ) {
        Text(
            text = "Team Manager",
            modifier = Modifier.padding(start = 20.dp, top = 20.dp),
            fontSize = 20.sp,
            color = com.example.shedula_next_try.ui.theme.unserSchwarz
        )

        // Display team members
        Text(
            text = "Team Members",
            modifier = Modifier.padding(start = 20.dp, top = 20.dp),
            fontSize = 20.sp,
            color = com.example.shedula_next_try.ui.theme.unserSchwarz
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        ) {
            team.teammates.forEach { teammate ->
                Text(
                    text = teammate.username,
                    fontSize = 16.sp,
                    modifier = Modifier.padding(vertical = 8.dp)
                )
            }
        }

        // Add Teammate section
        Text(
            text = "Add Teammate",
            modifier = Modifier.padding(start = 20.dp, top = 20.dp),
            fontSize = 20.sp,
            color = com.example.shedula_next_try.ui.theme.unserSchwarz
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

        // Add Button
        Button(
            onClick = {
                viewModel.addTeammate(username)

                Toast.makeText(
                    context,
                    "Teammate added successfully.",
                    Toast.LENGTH_SHORT
                ).show()

                // Clear input fields
                username = ""
                password = ""
                role = Role.EMPLOYEE
                workhours = 0.0
                vacationDays = 0
            },
            modifier = Modifier
                .padding(top = 20.dp)
                .width(IntrinsicSize.Max)
                .height(50.dp),
            colors = ButtonDefaults.buttonColors(
                contentColor = com.example.shedula_next_try.ui.theme.unserSchwarz,
                containerColor = com.example.shedula_next_try.ui.theme.leichtesGrau
            )
        ) {
            Text(
                text = "Add Teammate",
                fontSize = 16.sp
            )
        }

        // Remove Button
        Button(
            onClick = {
                viewModel.removeTeammate(username)

                Toast.makeText(
                    context,
                    "Teammate removed successfully.",
                    Toast.LENGTH_SHORT
                ).show()

                // Clear input field
                username = ""
            },
            modifier = Modifier
                .padding(top = 20.dp)
                .width(IntrinsicSize.Max)
                .height(50.dp),
            colors = ButtonDefaults.buttonColors(
                contentColor = com.example.shedula_next_try.ui.theme.unserSchwarz,
                containerColor = com.example.shedula_next_try.ui.theme.leichtesGrau
            )
        ) {
            Text(
                text = "Remove Teammate",
                fontSize = 16.sp
            )
        }

        // Back Button
        Button(
            onClick = {
                navController.navigate("LoginScreen")
            },
            modifier = Modifier
                .padding(top = 20.dp)
                .width(IntrinsicSize.Max)
                .height(50.dp),
            colors = ButtonDefaults.buttonColors(
                contentColor = com.example.shedula_next_try.ui.theme.unserSchwarz,
                containerColor = com.example.shedula_next_try.ui.theme.leichtesGrau
            )
        ) {
            Text(
                text = "Back",
                fontSize = 16.sp
            )
        }
    }
}