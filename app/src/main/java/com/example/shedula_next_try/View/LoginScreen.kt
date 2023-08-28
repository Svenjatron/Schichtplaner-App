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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.shedula_next_try.Model.LocalNavController
import com.example.shedula_next_try.Model.MainViewModel


val leichtesGrau = Color(0xFFF2F2F2)
val unserSchwarz = Color(0xFF000000)
val unserOcker = Color(0xFFF2CF66)
val weiss = Color(0XFFFFFFFF)
val orange1 = Color(0XFFF24405)
val orange2 = Color(0XFFF26716)

@Composable
fun LoginScreen(viewModel: MainViewModel) {
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
            Button(
                onClick = { navController.navigate("LoginManager") },
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
                    text = "Login",
                    fontSize = 40.sp
                )
            }
            Button(
                onClick = { navController.navigate("ErsteinrichtungScreen") },
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
                    text = "Ersteinrichtung",
                    fontSize = 36.sp
                )
            }
        }
    }
}

