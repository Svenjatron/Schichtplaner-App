package com.example.shedula_next_try.View

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.shedula_next_try.Model.MainViewModel
import java.util.Date

@Composable
fun NfcTimePunchScreen(viewModel: MainViewModel) {
    val punchInTime = remember { mutableStateOf(0L) }
    val punchOutTime = remember { mutableStateOf(0L) }
    val hoursWorked = remember { mutableStateOf("") }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = "Bitte jetzt das Handy an den NFC-Tag halten",
            fontSize = 30.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(horizontal = 16.dp)
        )

        Text(
            text = if (punchInTime.value != 0L) "Punch In Time: ${Date(punchInTime.value)}" else "Punch In Time: --:--:--",
            fontSize = 20.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(horizontal = 16.dp)
        )

        Text(
            text = if (punchOutTime.value != 0L) "Punch Out Time: ${Date(punchOutTime.value)}" else "Punch Out Time: --:--:--",
            fontSize = 20.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(horizontal = 16.dp)
        )

        Text(
            text = "Hours worked: ${hoursWorked.value}",
            fontSize = 20.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(horizontal = 16.dp)
        )
    }
}
