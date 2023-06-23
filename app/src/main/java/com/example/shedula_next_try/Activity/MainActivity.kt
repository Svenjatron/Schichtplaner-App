package com.example.shedula_next_try.Activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.compose.ui.tooling.preview.Preview
import com.example.shedula_next_try.View.*
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import androidx.compose.ui.platform.LocalContext
import com.google.firebase.FirebaseApp
import androidx.lifecycle.ViewModelProvider
import com.example.shedula_next_try.AdminRegister
import com.example.shedula_next_try.Model.LocalNavController
import com.example.shedula_next_try.Model.MainViewModel
import com.example.shedula_next_try.Model.Role
import com.example.shedula_next_try.Model.User

class MainActivity : ComponentActivity() {

    private lateinit var viewModel: MainViewModel
    private var admin: User? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FirebaseApp.initializeApp(this)
        val firebase : DatabaseReference = FirebaseDatabase.getInstance().getReference()
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        setContent {
            ShedulaNextTryApp(viewModel)
        }
    }
}


@Composable
fun ShedulaNextTryApp(viewModel: MainViewModel) {
    val navController = rememberNavController()
    val context = LocalContext.current

    CompositionLocalProvider(LocalNavController provides navController) {
        NavHost(navController = navController, startDestination = "LoginScreen") {
            composable("LoginScreen") {
                LoginScreen()
            }
            composable("LoginManager") {
                LoginManager()
            }
            composable("ErsteinrichtungScreen") {
                ErsteinrichtungScreen()
            }
            composable("TeamManager") {
                TeamManager()
            }
            composable("EmployeeScreen") {
                EmployeeScreen()
            }
            composable("AdminScreen") {
                AdminScreen()
            }
            composable("ZeiterfassungsScreen") {
                ZeiterfassungsScreen()
            }
            composable("NFCKontakt1Screen") {
                NFCKontakt1Screen()
            }
            composable("KalenderScreen") {
                KalenderScreen(viewModel)
            }
            composable("TeamVerwaltung") {
                viewModel.team?.let { team ->
                    TeamVerwaltung(team, context, viewModel)
                }
            }
            composable("AdminRegister") {
                AdminRegister(context, viewModel)
            }
            composable("NfcTimePunchScreen") {
                NfcTimePunchScreen(viewModel)
            }
        }
    }
}

@Preview
@Composable
fun PreviewShedulaNextTryApp() {
    ShedulaNextTryApp(MainViewModel())
}

