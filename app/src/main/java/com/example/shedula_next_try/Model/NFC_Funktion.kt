package com.example.shedula_next_try.Model

import android.content.Intent
import android.nfc.NfcAdapter
import android.os.Bundle
import android.widget.Toast
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.util.Date

class MainActivity : AppCompatActivity() {
    private var punchInTime = mutableStateOf(0L)
    private var punchOutTime = mutableStateOf(0L)
    private var hoursWorked = mutableStateOf("")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
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
                    text = "Hours worked: $hoursWorked",
                    fontSize = 20.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
            }
        }

        // ... Rest of your onCreate code
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)

        if (NfcAdapter.ACTION_NDEF_DISCOVERED == intent?.action || NfcAdapter.ACTION_TAG_DISCOVERED == intent?.action) {
            if (punchInTime.value == 0L) {
                punchInTime.value = System.currentTimeMillis()
                Toast.makeText(this, "Punch in recorded", Toast.LENGTH_SHORT).show()
            } else if (punchOutTime.value == 0L) {
                punchOutTime.value = System.currentTimeMillis()
                Toast.makeText(this, "Punch out recorded", Toast.LENGTH_SHORT).show()
                calculateTimeDifference()
            } else {
                // Reset if both times are already recorded
                punchInTime.value = 0
                punchOutTime.value = 0
                hoursWorked.value = ""
                Toast.makeText(this, "Times reset", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun calculateTimeDifference() {
        val diff = punchOutTime.value - punchInTime.value

        var seconds = diff / 1000
        var minutes = seconds / 60
        val hours = minutes / 60

        minutes %= 60
        seconds %= 60

        // Now you can use hours and minutes
        hoursWorked.value = "$hours:$minutes"
    }
}