package com.example.shedula_next_try.View

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.shedula_next_try.Model.LocalNavController
import com.example.shedula_next_try.Model.MainViewModel

@Composable
fun ErsteinrichtungScreen(viewModel: MainViewModel) {
    val navController = LocalNavController.current
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
                        colors = listOf(com.example.shedula_next_try.ui.theme.unserOcker, com.example.shedula_next_try.ui.theme.orange2),
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
                        colors = listOf(orange2, unserOcker),
                        startX = 0f,
                        endX = 500f
                    )
                )
                .align(Alignment.BottomCenter)
        )

        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Für die Ersteinrichtung ist es zunächst erforderlich, einen Administrator oder ein:e Teammanager:in mit einem Benutzernamen und Passwort zu registrieren. Anschließend kann die:der Teammanager:in weitere Mitglieder hinzufügen oder entfernen, indem sie ihnen Benutzer:innennamen und Passwort zuweist. Die Teammitglieder können sich nach dem Login in ihr Konto einloggen und ihre Sollstunden erfassen. Außerdem haben sie Zugriff auf einen bereitgestellten Kalender, in dem sie ihre Arbeitszeiten planen können, sowie die Möglichkeit, die NFC-Zeiterfassung zu nutzen. Die:der Teammanager:in hat eine Übersicht über die geleisteten Stunden der Teammitglieder und kann den Arbeitsplan einsehen.",
                fontSize = 20.sp,
                color = Color.White,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(horizontal = 16.dp)
            )

            Button(
                onClick = { navController.navigate("AdminRegister") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 20.dp, start = 20.dp, end = 20.dp)
                    .height(80.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = leichtesGrau,
                    contentColor = unserSchwarz
                )
            ) {
                Text(
                    text = "Loslegen",
                    fontSize = 45.sp
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
