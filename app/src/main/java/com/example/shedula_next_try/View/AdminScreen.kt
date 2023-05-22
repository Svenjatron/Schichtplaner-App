package com.example.shedula_next_try.View

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun AdminScreen(navController: NavController) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(com.example.shedula_next_try.ui.theme.unserSchwarz)
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
            Button(
                onClick = { /* Zur Login-Seite */ },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 20.dp, start = 20.dp, end = 20.dp)
                    .height(130.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = com.example.shedula_next_try.ui.theme.leichtesGrau,
                    contentColor = com.example.shedula_next_try.ui.theme.unserSchwarz
                )
            ) {
                Text(
                    text = "Arbeitszeitmanagement",
                    fontSize = 28.sp
                )
            }
            Button(
                onClick = { navController.navigate("ZeiterfassungsScreen") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 20.dp, start = 20.dp, end = 20.dp)
                    .height(130.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = com.example.shedula_next_try.ui.theme.leichtesGrau,
                    contentColor = com.example.shedula_next_try.ui.theme.unserSchwarz
                )
            ) {
                Text(
                    text = "Team-Management",
                    fontSize = 28.sp
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Home Button
            Button(
                onClick = {
                    navController.navigate("LoginManager")
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
        }
    }
}
