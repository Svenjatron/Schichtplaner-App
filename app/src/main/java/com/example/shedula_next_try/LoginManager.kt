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

import com.example.shedula_next_try.User


@Composable
fun LoginManager() {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val usernameState = remember { mutableStateOf("") }
    val passwordState = remember { mutableStateOf("") }
    var text by remember { mutableStateOf("") }

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
                        colors = listOf(unserOcker, com.example.shedula_next_try.ui.theme.orange2),
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
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {

            Text(
                text = "Username",
                modifier = Modifier.padding(start = 20.dp, top = 20.dp),
                fontSize = 20.sp
            )

            BasicTextField(
                value = text,
                onValueChange = { text = it },
                textStyle = MaterialTheme.typography.bodyLarge,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .background(com.example.shedula_next_try.ui.theme.leichtesGrau, shape = RoundedCornerShape(8.dp))
                    .padding(horizontal = 20.dp, vertical = 20.dp)
            )

            Text(
                text = "Passwort",
                modifier = Modifier.padding(start = 20.dp, top = 20.dp),
                fontSize = 20.sp
            )

            BasicTextField(
                value = text,
                onValueChange = { text = it },
                textStyle = MaterialTheme.typography.bodyLarge,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .background(com.example.shedula_next_try.ui.theme.leichtesGrau, shape = RoundedCornerShape(8.dp))
                    .padding(horizontal = 20.dp, vertical = 20.dp)
            )

            Spacer(modifier = Modifier.height(32.dp))


            Button(
                onClick = { /* Nach Eingabe der Login-Daten */ },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 20.dp, start = 20.dp, end = 20.dp)
                    .height(130.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = leichtesGrau,
                    contentColor = unserSchwarz
                )
            ) {
                Text(
                    text = "Anmelden",
                    fontSize = 45.sp
                )
            }
        }
    }
}