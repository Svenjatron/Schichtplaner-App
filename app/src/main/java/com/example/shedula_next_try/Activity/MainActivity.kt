package com.example.shedula_next_try.Activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
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

    NavHost(navController = navController, startDestination = "LoginScreen") {
        composable("LoginScreen") {
            LoginScreen(navController)
        }
        composable("LoginManager") {
            LoginManager(navController)
        }
        composable("ErsteinrichtungScreen") {
            ErsteinrichtungScreen(navController)
        }
        composable("TeamManager") {
            TeamManager(navController)
        }
        composable("EmployeeScreen") {
            EmployeeScreen(navController)
        }
        composable("AdminScreen") {
            AdminScreen(navController)
        }
        composable("ZeiterfassungsScreen") {
            ZeiterfassungsScreen(navController)
        }
        composable("NFCKontakt1Screen") {
            NFCKontakt1Screen(navController)
        }
        composable("KalenderScreen") {
            KalenderScreen(navController)
        }
        composable("TeamVerwaltung") {
            viewModel.team?.let { team ->
                TeamVerwaltung(navController, team, context, viewModel)
            }
        }
        composable("AdminRegister") {
            AdminRegister(navController, context) { admin, team ->
                viewModel.createAdmin(admin, team)
            }
        }



    }
}

@Preview
@Composable
fun PreviewShedulaNextTryApp() {
    ShedulaNextTryApp(MainViewModel())
}

