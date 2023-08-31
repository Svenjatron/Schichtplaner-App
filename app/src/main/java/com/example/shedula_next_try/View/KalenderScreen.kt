package com.example.shedula_next_try.View

import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.compose.material3.*
import com.example.shedula_next_try.Model.MainViewModel
import java.time.LocalDate
import android.app.DatePickerDialog
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import com.example.shedula_next_try.Model.LocalNavController
import com.example.shedula_next_try.R
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch
import java.util.Calendar


    @Composable
    fun KalenderScreen(viewModel: MainViewModel) {
        val navController = LocalNavController.current
        val context = LocalContext.current

        val selectedDate = remember { mutableStateOf<LocalDate?>(null) }
        val dateSelected = remember { mutableStateOf(false) }
        val showDialog = remember { mutableStateOf(false) }

        val enteredWorkingHours = remember { mutableStateOf(TextFieldValue()) }
        val enteredVacationDays = remember { mutableStateOf(TextFieldValue()) }

        val viewModelScope = rememberCoroutineScope()

        // DatePicker wird beim Aufrufen direkt angezeigt
        LaunchedEffect(Unit) {
            showDatePicker(context, selectedDate, showDialog, onDialogCancel = {navController.navigate("EmployeeScreen")}, dateSelected = dateSelected)
        }

        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {

            if (showDialog.value) {
                ShowEntryDialog(
                    onDismiss = {
                        navController.navigate("KalenderScreen")
                    },
                    enteredWorkingHours,
                    enteredVacationDays,
                    onConfirm = {
                        val hours = enteredWorkingHours.value.text.toDoubleOrNull() ?: 0.0
                        val days = enteredVacationDays.value.text.toIntOrNull() ?: 0
                        val currentUser = FirebaseAuth.getInstance().currentUser
                        val uid = currentUser?.uid ?: ""
                        val date = selectedDate.value?.toString() ?: ""

                        viewModelScope.launch {
                            viewModel.addEntryToFirestore(date, hours, days)
                        }

                        enteredWorkingHours.value = TextFieldValue("")
                        enteredVacationDays.value = TextFieldValue("")
                        showDialog.value = false
                        dateSelected.value = false
                        navController.navigate("KalenderScreen")

                    }
                )
            }

        }
    }

    @Composable
    fun ShowEntryDialog(
        onDismiss: () -> Unit,
        enteredWorkingHours: MutableState<TextFieldValue>,
        enteredVacationDays: MutableState<TextFieldValue>,
        onConfirm: () -> Unit
    ) {
        Dialog(onDismissRequest = onDismiss, properties = DialogProperties()) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(text = "Arbeitszeit (Stunden) unter dem Text eintragen:")
                BasicTextField(
                    value = enteredWorkingHours.value,
                    onValueChange = { enteredWorkingHours.value = it },
                    Modifier.background(Color.White)
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(text = "Urlaubstage unter dem Text eintragen:")
                BasicTextField(
                    value = enteredVacationDays.value,
                    onValueChange = { enteredVacationDays.value = it },
                    Modifier.background(Color.White)

                )

                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Button(
                        onClick = onDismiss,
                        colors = ButtonDefaults.buttonColors(containerColor = unserOcker)
                    ) {
                        Text("Zurück")
                    }

                    Button(
                        onClick = onConfirm,
                        colors = ButtonDefaults.buttonColors(containerColor = unserOcker)
                    ) {
                        Text("Eintragen")
                    }
                }
            }
        }
    }

    fun showDatePicker(
        context: android.content.Context,
        selectedDate: MutableState<LocalDate?>,
        showDialog: MutableState<Boolean>,
        onDialogCancel: ()-> Unit,
        dateSelected: MutableState<Boolean>
    ) {
        val calendar = Calendar.getInstance()
        val dialog = DatePickerDialog(
            context,
            R.style.CustomDatePickerDialogTheme,
            { _, year, month, dayOfMonth ->
                val date = LocalDate.of(year, month + 1, dayOfMonth)
                selectedDate.value = date
                dateSelected.value = true
                showDialog.value = true
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )
        dialog.setOnCancelListener {
            if (!dateSelected.value) {
                onDialogCancel()
                Log.d("DatePicker", "DatePicker wurde abgebrochen.")
            }
        }


        dialog.show()
    }

