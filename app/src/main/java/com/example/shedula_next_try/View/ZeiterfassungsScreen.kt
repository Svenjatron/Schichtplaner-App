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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.shedula_next_try.Model.LocalNavController

@Composable
fun ZeiterfassungsScreen() {
    val navController = LocalNavController.current
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
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
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 20.dp, start = 20.dp, end = 20.dp)
                    .height(180.dp)
                    .background(color = leichtesGrau)
            ) {

                // Hier Abfrage ob Zeiterfassung aktiviert wurde

                Text(
                    text = "Um die Arbeitszeit aufzuzeichnen, drücken Sie bitte den entsprechenden Button, halten Sie dann Ihr Handy vor den NFC-Tag, um die Zeiterfassung zu starten.",
                    fontSize = 21.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(16.dp)
                )

                // Text(
                //    text = "Um die Zeiterfassung zu beenden, klicken Sie bitte auf den entsprechenden Button und halten Sie Ihr Handy erneut vor den NFC-Tag.",
                //    fontSize = 21.sp,
                //    textAlign = TextAlign.Center,
                //    modifier = Modifier.padding(16.dp)
                // )
            }
            Button(
                onClick = { navController.navigate("NFCKontakt1Screen") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 20.dp, start = 20.dp, end = 20.dp)
                    .height(130.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = com.example.shedula_next_try.ui.theme.unserOcker,
                    contentColor = com.example.shedula_next_try.ui.theme.unserSchwarz
                )
            ) {

                // Hier Abfrage ob Zeiterfassung aktiviert wurde

                Text(
                    text = "Zeiterfassung starten",
                    fontSize = 28.sp
                )
                // Text(
                //    text = "Zeiterfassung stoppen",
                //    fontSize = 28.sp
                // )

            }

            Spacer(modifier = Modifier.height(20.dp))

            Button(
                onClick = {
                    navController.navigate("EmployeeScreen")
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