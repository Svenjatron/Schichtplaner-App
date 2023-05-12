package com.example.shedula_next_try

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.Image
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

val leichtesGrau = Color(0xFFF2F2F2)
val unserSchwarz = Color(0xFF000000)
val unserOcker = Color(0xFFF2CF66)
val weiss = Color(0XFFFFFFFF)
val orange1 = Color(0XFFF24405)
val orange2 = Color(0XFFF26716)

@Composable
fun LoginScreen() {
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
                        colors = listOf(orange2, unserOcker),
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
                        colors = listOf(unserOcker, orange2),
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
                    containerColor = leichtesGrau,
                    contentColor = unserSchwarz
                )
            ) {
                Text(
                    text = "Login",
                    fontSize = 50.sp
                )
            }
            Button(
                onClick = { /* Zur Ersteinrichtung */ },
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
                    text = "Team anlegen",
                    fontSize = 45.sp
                )
            }
        }
    }
}


