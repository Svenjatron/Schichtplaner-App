package com.example.shedula_next_try.View

import android.widget.CalendarView
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.window.DialogProperties
import com.example.shedula_next_try.Model.LocalNavController
import com.example.shedula_next_try.Model.MainViewModel
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

@Composable
fun KalenderScreen(viewModel: MainViewModel) {
    val navController = LocalNavController.current
    val context = LocalContext.current
    val selectedDate = remember { mutableStateOf("") }
    val entries = viewModel.getAllEntries()

    val showDialog = remember { mutableStateOf(false) }

    val viewModelScope = rememberCoroutineScope()

    val enteredWorkingHours = remember { mutableStateOf("") }
    val enteredVacationDays = remember { mutableStateOf("") }

    // Zeig PopUp
    fun showPopupDialog() {
        showDialog.value = true
    }

    fun hidePopupDialog() {
        showDialog.value = false

        val date = selectedDate.value
        val hours = enteredWorkingHours.value.toDoubleOrNull() ?: 0.0
        val days = enteredVacationDays.value.toIntOrNull() ?: 0

        runBlocking {
            val currentUser = FirebaseAuth.getInstance().currentUser
            val username = currentUser?.email?.substringBefore('@') ?: ""
            viewModel.addEntry(username, date, hours, days)
        }

        enteredWorkingHours.value = ""
        enteredVacationDays.value = ""
    }



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
                    val calendarView = CalendarView(context)
                    calendarView.setOnDateChangeListener { _, year, month, dayOfMonth ->
                        selectedDate.value = "$year-${month + 1}-$dayOfMonth"
                        showPopupDialog() // OnClick PopUp
                    }
                    calendarView
                },
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .height(500.dp)
                    .width(400.dp)
            )

            Spacer(modifier = Modifier.height(20.dp))

            // Theoretisch sollten hier Entries angezeigt werden :(
            Column {
                Text(
                    text = "Einträge:",
                    modifier = Modifier.padding(top = 20.dp),
                    fontSize = 18.sp,
                    color = com.example.shedula_next_try.ui.theme.unserSchwarz,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(10.dp))

                if (entries.isEmpty()) {
                    Text(
                        text = "Keine Einträge vorhanden",
                        modifier = Modifier.padding(bottom = 20.dp),
                        color = com.example.shedula_next_try.ui.theme.unserSchwarz,
                        textAlign = TextAlign.Center
                    )
                } else {
                    entries.forEach { entry ->
                        Text(
                            text = "${entry.date}: Arbeitszeit ${entry.workingHours}h, Urlaubstage ${entry.vacationDays}",
                            modifier = Modifier.padding(bottom = 5.dp),
                            color = com.example.shedula_next_try.ui.theme.unserSchwarz,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }

            Button(
                onClick = {
                    navController.navigate("EmployeeScreen")
                },
                modifier = Modifier
                    .width(180.dp)
                    .height(50.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = com.example.shedula_next_try.ui.theme.leichtesGrau,
                    contentColor = com.example.shedula_next_try.ui.theme.unserSchwarz
                )
            ) {
                Text(text = "Zurück", fontSize = 16.sp)
            }
        }

        // PopUP
        if (showDialog.value) {
            AlertDialog(
                onDismissRequest = { showDialog.value = false },
                title = { Text(text = "Eintrag hinzufügen") },
                text = {
                    Column {
                        Text(text = "Arbeitszeit (Stunden):")

                        BasicTextField(
                            value = enteredWorkingHours.value,
                            onValueChange = { enteredWorkingHours.value = it }
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        Text(text = "Urlaubstage:")

                        BasicTextField(
                            value = enteredVacationDays.value,
                            onValueChange = { enteredVacationDays.value = it }
                        )
                    }
                },
                confirmButton = {
                    TextButton(
                        onClick = {
                            val hours = enteredWorkingHours.value.toDoubleOrNull() ?: 0.0
                            val days = enteredVacationDays.value.toIntOrNull() ?: 0

                            viewModelScope.launch {
                                withContext(Dispatchers.IO) {
                                    val currentUser = FirebaseAuth.getInstance().currentUser
                                    val username = currentUser?.email?.substringBefore('@') ?: ""
                                    val date = selectedDate.value

                                    viewModel.addEntry(username, date, hours, days)
                                }
                            }
                            hidePopupDialog()
                        },
                        colors = ButtonDefaults.textButtonColors(contentColor = com.example.shedula_next_try.ui.theme.leichtesGrau),
                        content = { Text("Eintragen", fontSize = 16.sp) }
                    )
                }

                ,
                dismissButton = {
                    TextButton(
                        onClick = { showDialog.value = false },
                        colors = ButtonDefaults.textButtonColors(contentColor = com.example.shedula_next_try.ui.theme.leichtesGrau),
                        content = { Text("Abbrechen", fontSize = 16.sp) }
                    )
                },
                properties = DialogProperties(dismissOnBackPress = false)
            )
        }
    }
}
