package com.example.shedula_next_try

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable




class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ShedulaNextTryApp()
        }
    }
}

@Composable
fun ShedulaNextTryApp() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "LoginScreen") {
        composable("LoginScreen") {
            LoginScreen(navController)
        }
        composable("LoginManager") {
            LoginManager(navController)
        }
        composable("TeamManager") {
            TeamManager(navController)
        }
        composable("EmployeeScreen") {
            TeamManager(navController)
        }
    }
}

@Preview
@Composable
fun PreviewShedulaNextTryApp() {
    ShedulaNextTryApp()
}





