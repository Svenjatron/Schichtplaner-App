package com.example.shedula_next_try.View

import android.view.Gravity
import android.widget.CalendarView
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.shedula_next_try.Model.LocalNavController
import com.example.shedula_next_try.ui.theme.unserSchwarz
import java.lang.reflect.Field


@Composable
fun KalenderScreen() {
    val navController = LocalNavController.current
    val context = LocalContext.current

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {


        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(80.dp)
                    .background(color = com.example.shedula_next_try.ui.theme.unserOcker)
            ) {
                Text(
                    text = "Arbeitszeitmanagement",
                    fontSize = 30.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(16.dp)
                )
            }

            Spacer(modifier = Modifier.height(20.dp))


            Text(
                text = "Bitte Arbeitszeiten und Urlaub eintragen:",
                modifier = Modifier.padding(top = 20.dp),
                fontSize = 20.sp,
                color = com.example.shedula_next_try.ui.theme.unserSchwarz,
                textAlign = TextAlign.Center

            )


            Spacer(modifier = Modifier.height(20.dp))

            AndroidView(
                factory = { context ->
                    CalendarView(context)
                },
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .height(500.dp)
                    .width(400.dp)

            )


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

