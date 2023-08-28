package com.example.shedula_next_try.View

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.shedula_next_try.Model.MainViewModel
import java.util.Date

@Composable
fun NfcTimePunchScreen(viewModel: MainViewModel) {
    // Assuming punchInTime, punchOutTime, and hoursWorked are LiveData types
    val punchInTime = viewModel.punchInTime.observeAsState(initial = null)
    val punchOutTime = viewModel.punchOutTime.observeAsState(initial = null)
    val hoursWorked = viewModel.hoursWorked.observeAsState(initial = "")

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
            text = punchInTime.value?.let { "Punch In Time: ${Date(it)}" } ?: "Punch In Time: --:--:--",
            fontSize = 20.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(horizontal = 16.dp)
        )

        Text(
            text = punchOutTime.value?.let { "Punch Out Time: ${Date(it)}" } ?: "Punch Out Time: --:--:--",
            fontSize = 20.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(horizontal = 16.dp)
        )


    }
}
